package org.cai.udf

import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.cai.tools.convert.{Complement, Convert}
import org.cai.tools.translate.{Decoding, TranslateTools}
import org.neo4j.procedure.{Description, Name, UserFunction}

/**
 * @author cai584770
 * @date 2024/8/13 23:05
 * @Version
 */
class GeSeqFunctions {

  @UserFunction
  @Description("Process custom data type")
  def fromFASTQ(@Name("input") input: String): Map[String, Any] = {
    val (information, sequence) = FileProcess.getInformationAndSequence(input)
    val normalizeSequence = FileNormalize.remove(sequence)

    val map = GeSeq.fromSequence(normalizeSequence).toMap
    map
  }

  @UserFunction
  @Description("Process custom data type")
  def fromFASTA(@Name("input") input: String): Map[String, Any] = {
    val (information, sequence) = FileProcess.getInformationAndSequence(input)
    val normalizeSequence = FileNormalize.remove(sequence)

    GeSeq.fromSequence(normalizeSequence).toMap
  }


  @UserFunction
  @Description("Process custom data type")
  def translateGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    val result: String = TranslateTools.translate(bytes)
    result
  }

  @UserFunction
  @Description("Process custom data type")
  def reverseGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Convert.convertDirect(bytes)).toString()

  }

  @UserFunction
  @Description("Process custom data type")
  def complementGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(bytes)).toString()
  }

  @UserFunction
  @Description("Process custom data type")
  def reverseComplementGene(@Name("input") input:  Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(Convert.convertDirect(bytes))).toString()
  }


}
