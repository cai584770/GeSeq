package org.cai.geseq

import org.cai.bbm.BBM
import org.cai.geseq.BioSequenceEnum.{BioSequenceType, DNA}
import org.cai.serialize.DeSerializeBase
import org.postgresql.util.PGobject

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.Base64

/**
 * @author cai584770
 * @date 2024/8/13 21:44
 * @Version
 */
case class GeSeq(
                  sequence: Array[Byte],
                  lowercase: List[(Int, Int)],
                  nBase: List[(Int, Int)],
                  otherBASE: List[(Int, String)],
                  sequenceLength: Long,
                  nucleotidesLength: Long
                ) {

  def getbbm = sequence

  def toNeo4jMap: Map[String, Any] = {
    Map(
      "sequence" -> Base64.getEncoder.encodeToString(sequence),
      "lowercase" -> lowercase.map { case (start, length) => Map("start" -> start, "length" -> length) },
      "nBase" -> nBase.map { case (start, length) => Map("start" -> start, "length" -> length) },
      "otherBASE" -> otherBASE.map { case (key, value) => Map("key" -> key, "value" -> value) },
      "sequenceLength" -> sequenceLength,
      "nucleotidesLength" -> nucleotidesLength
    )
  }

  def toByteArray: Array[Byte] = {
    val buffer = ByteBuffer.allocate(calculateTotalSize)

    buffer.putInt(sequence.length)
    buffer.put(sequence)

    buffer.putInt(lowercase.size)
    lowercase.foreach { case (start, length) =>
      buffer.putInt(start)
      buffer.putInt(length)
    }

    buffer.putInt(nBase.size)
    nBase.foreach { case (start, length) =>
      buffer.putInt(start)
      buffer.putInt(length)
    }

    buffer.putInt(otherBASE.size)
    otherBASE.foreach { case (start, value) =>
      buffer.putInt(start)
      val valueBytes = value.getBytes("UTF-8")
      buffer.putInt(valueBytes.length)
      buffer.put(valueBytes)
    }

    buffer.putLong(sequenceLength)
    buffer.putLong(nucleotidesLength)

    buffer.array()
  }

  private def calculateTotalSize: Int = {
    val bbmSize = 4 + sequence.length
    val lowercaseSize = 4 + lowercase.size * (4 + 4)
    val nBaseSize = 4 + nBase.size * (4 + 4)
    val otherBASESize = 4 + otherBASE.map { case (start, value) =>
      4 + 4 + value.getBytes("UTF-8").length
    }.sum
    val lengthSize = 8 + 8

    bbmSize + lowercaseSize + nBaseSize + otherBASESize + lengthSize
  }
}


object GeSeq {

  def fromSequence(data: String): GeSeq = {
    val (noLowerCaseSequence, lowerCaseList) = BBM.findConsecutiveLowerCasePositions(data)
    val (noNSequence, nCaseList) = BBM.removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = BBM.removeAndRecord(noNSequence)

    val sequence2bit: Array[Byte] = BBM.convertToBinaryArray(agctSequence)

    new GeSeq(sequence2bit, lowerCaseList, nCaseList, otherCaseList, data.length, agctSequence.length)
  }

  def extractBbm(bytes: Array[Byte]): Array[Byte] = {
    val buffer = ByteBuffer.wrap(bytes)

    val bbmLength = buffer.getInt
    val bbm = new Array[Byte](bbmLength)
    buffer.get(bbm)

    bbm
  }


  def convertGeSeqToNeo4jFormat(geSeq: GeSeq): Map[String, Any] = {
    Map(
      "sequence" -> Base64.getEncoder.encodeToString(geSeq.sequence),
      "lowercase" -> geSeq.lowercase.map { case (a, b) => Map("first" -> a, "second" -> b) },
      "nBase" -> geSeq.nBase.map { case (a, b) => Map("first" -> a, "second" -> b) },
      "otherBASE" -> geSeq.otherBASE.map { case (a, b) => Map("key" -> a, "value" -> b) },
      "sequenceLength" -> geSeq.sequenceLength,
      "nucleotidesLength" -> geSeq.nucleotidesLength
    )
  }

  def fromByteArray(bytes: Array[Byte]): GeSeq = {
    val buffer = ByteBuffer.wrap(bytes)

    val bbmLength = buffer.getInt
    val bbm = new Array[Byte](bbmLength)
    buffer.get(bbm)

    val lowercaseSize = buffer.getInt
    val lowercase = (for (_ <- 1 to lowercaseSize) yield {
      val start = buffer.getInt
      val length = buffer.getInt
      (start, length)
    }).toList

    val nBaseSize = buffer.getInt
    val nBase = (for (_ <- 1 to nBaseSize) yield {
      val start = buffer.getInt
      val length = buffer.getInt
      (start, length)
    }).toList

    val otherBASESize = buffer.getInt
    val otherBASE = (for (_ <- 1 to otherBASESize) yield {
      val start = buffer.getInt
      val valueLength = buffer.getInt
      val valueBytes = new Array[Byte](valueLength)
      buffer.get(valueBytes)
      val value = new String(valueBytes, StandardCharsets.UTF_8)
      (start, value)
    }).toList

    val sequenceLength = buffer.getLong
    val nucleotidesLength = buffer.getLong

    GeSeq(bbm, lowercase, nBase, otherBASE, sequenceLength, nucleotidesLength)
  }

  def fromPostgreSQLResult(anyRef: AnyRef):GeSeq={
    val pgObj: PGobject = anyRef.asInstanceOf[PGobject]

    val value = pgObj.getValue
    val myGeseq = value.stripPrefix("(").stripSuffix(")").split(",").map(_.trim)

    val sequence = myGeseq(0).asInstanceOf[Array[Byte]]
    val lowercaseBytes = myGeseq(1).asInstanceOf[Array[Byte]]
    val nBaseBytes = myGeseq(2).asInstanceOf[Array[Byte]]
    val otherBASEBytes = myGeseq(3).asInstanceOf[Array[Byte]]
    val sequenceLength = myGeseq(4).asInstanceOf[Long]
    val nucleotidesLength = myGeseq(5).asInstanceOf[Long]

    val lowercase = DeSerializeBase.deserializeListOfPairs(lowercaseBytes)
    val nBase = DeSerializeBase.deserializeListOfPairs(nBaseBytes)
    val otherBASE = DeSerializeBase.deserializeListOfPairWithString(otherBASEBytes)

    GeSeq(sequence, lowercase, nBase, otherBASE, sequenceLength, nucleotidesLength)
  }

}