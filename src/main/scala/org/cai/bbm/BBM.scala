package org.cai.bbm

import org.cai.exception.BioSequenceTypeException
import org.cai.file.FileNormalize
import org.cai.geseq.BioSequenceEnum.{BioSequenceType, DNA, RNA}

import scala.collection.mutable.ListBuffer

/**
 * @author cai584770
 * @date 2024/8/16 18:36
 * @Version
 */
object BBM {

  def bbm(data: String, bioSequenceType: BioSequenceType = DNA): (Map[String, List[(Any, Any)]], Array[Byte]) = {
    val sequence = FileNormalize.remove(data)
    val sequenceLength = sequence.length

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)

    val agctSequenceLength = agctSequence.length
    val lengthList: List[(Int, Int)] = List((sequenceLength, agctSequenceLength))

    val sequence2bit: Array[Byte] = convertToBinaryArray(agctSequence, bioSequenceType)

    val supplementaryInformation = Map(
      "LowerCasePosition" -> lowerCaseList,
      "NCasePosition" -> nCaseList,
      "OtherCaseList" -> otherCaseList,
      "Length" -> lengthList
    )

    (supplementaryInformation, sequence2bit)
  }

  def convertToBinaryArray(s: String, bioSequenceType: BioSequenceType): Array[Byte] = {
    val conversionMap = bioSequenceType match {
      case DNA => Map('A' -> "00", 'G' -> "10", 'C' -> "01", 'T' -> "11")
      case RNA => Map('A' -> "00", 'G' -> "10", 'C' -> "01", 'U' -> "11")
      case _ => throw new BioSequenceTypeException
    }

    val binaryStringBuilder = new StringBuilder()

    for (char <- s) {
      binaryStringBuilder.append(conversionMap.getOrElse(char, ""))
    }

    val binaryString = binaryStringBuilder.toString()

    val binaryArray = new Array[Byte](binaryString.length / 8 + (if (binaryString.length % 8 != 0) 1 else 0))

    for (i <- binaryArray.indices) {
      var byteValue = 0
      for (j <- 0 until 8) {
        val charIndex = i * 8 + j
        if (charIndex < binaryString.length && binaryString(charIndex) == '1') {
          byteValue |= (1 << (7 - j))
        }
      }
      binaryArray(i) = byteValue.toByte
    }

    binaryArray
  }

  def removeAndRecordN(s: String): (String, List[(Int, Int)]) = {
    val positions = ListBuffer[(Int, Int)]()
    val result = new StringBuilder()

    var start = 0
    var end = 0
    var prePosition = 0

    var length = 0
    while (end < s.length) {
      if (s(end) == 'N') {
        start = end
        while (end < s.length && s(end) == 'N') {
          end += 1
        }
        if (end < s.length) {
          result.append(s(end))
        }
        positions += ((start - prePosition - length, end - start))
        prePosition = start
        length = end - start

      } else {
        result.append(s(end))
      }
      end += 1
    }

    (result.toString, positions.toList)
  }

  def removeAndRecord(s: String): (String, List[(Int, String)]) = {
    val result = new StringBuilder(s)
    val positions = scala.collection.mutable.ListBuffer[(Int, String)]()
    var pos = 0

    var index = 0
    while (index < result.length) {
      val currentChar = result(index)
      if (!List('A', 'G', 'C', 'T').contains(currentChar)) {
        val start = index
        var subString = currentChar.toString
        result.deleteCharAt(index)
        while (index < result.length && !List('A', 'G', 'C', 'T').contains(result(index))) {
          subString += result(index)
          result.deleteCharAt(index)
        }
        positions += ((start - pos, subString))
        pos = start
      } else {
        index += 1
      }
    }

    (result.toString, positions.toList)
  }

  def findConsecutiveLowerCasePositions(s: String): (String, List[(Int, Int)]) = {
    val result = ListBuffer[(Int, Int)]()
    val sequenceBuilder = new StringBuilder()
    var startIndex = -1
    var length = 0
    var realStartPos = 0

    for ((c, index) <- s.zipWithIndex) {
      if (c.isLower) {
        sequenceBuilder += c.toUpper
        if (startIndex == -1) {
          startIndex = index
        }
        length += 1
      } else {
        sequenceBuilder += c
        if (startIndex != -1) {
          result += ((startIndex - realStartPos, length))
          realStartPos = startIndex + length
          startIndex = -1
          length = 0
        }
      }
    }

    if (startIndex != -1) {
      result += ((startIndex - realStartPos, length))
    }

    (sequenceBuilder.toString(), result.toList)
  }

}
