package org.cai.udf

import org.cai.geseq.GeSeq
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.{Decoding, TranslateTools}
import org.neo4j.procedure.{Description, Name, UserFunction}

/**
 * @author cai584770
 * @date 2024/8/13 23:05
 * @Version
 */
class GeSeqFunctions {

//  @UserFunction
//  @Description("Process custom data type")
//  def fromFASTQ(@Name("input") input: String): Map[String, Any] = {
//    val (information, sequence) = FileProcess.getInformationAndSequence(input)
//    val normalizeSequence = FileNormalize.remove(sequence)
//
//    val map = GeSeq.fromSequence(normalizeSequence).toMap
//    map
//  }

//  @UserFunction
//  @Description("Process custom data type")
//  def fromFASTA(@Name("input") input: String): Map[String, Any] = {
//    val (information, sequence) = FileProcess.getInformationAndSequence(input)
//    val normalizeSequence = FileNormalize.remove(sequence)
//
//    GeSeq.fromSequence(normalizeSequence).toMap
//  }


  @UserFunction(name = "translate")
  @Description("translate gene sequence to protein sequence")
  def translateGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    val result: String = TranslateTools.translate(bytes)
    result
  }

  @UserFunction(name = "reverseGene")
  @Description("reverse gene sequence")
  def reverseGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Reverse.reverseDirect(bytes)).toString()

  }

  @UserFunction(name = "complement")
  @Description("complement gene sequence")
  def complementGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(bytes)).toString()
  }

  @UserFunction(name = "rev_com")
  @Description("reverse and complement gene sequence")
  def reverseComplementGene(@Name("input") input:  Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(Reverse.reverseDirect(bytes))).toString()
  }


}
