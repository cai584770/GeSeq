package org.cai.udf

import org.cai.file.{FileNormalize, FileProcess}
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

  @UserFunction("geseq.fromFASTQ")
  @Description("get GeSeq from fastq file")
  def fromFASTQ(@Name("input") input: String): Array[Byte] = {
    val sequence: String = FileProcess.getSequenceFromFASTQ(input)

    val normalizeSequence = FileNormalize.remove(sequence)
    val seq: GeSeq = GeSeq.fromSequence(normalizeSequence)

    seq.toByteArray
  }


  @UserFunction("geseq.fromFASTA")
  @Description("get GeSeq from fasta file")
  def fromFASTA(@Name("input") input: String): Array[Byte] = {
    val (information, sequence) = FileProcess.getInformationAndSequence(input)
    val normalizeSequence = FileNormalize.remove(sequence)
    val seq: GeSeq = GeSeq.fromSequence(normalizeSequence)

    seq.toByteArray
  }


  @UserFunction("geseq.translate")
  @Description("translate gene sequence to protein sequence")
  def translateGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    val result = TranslateTools.translate(bytes)
    result.toString()
  }

  @UserFunction("geseq.rev")
  @Description("reverse gene sequence")
  def reverseGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Reverse.reverseDirect(bytes)).toString()

  }

  @UserFunction("geseq.com")
  @Description("complement gene sequence")
  def complementGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(bytes)).toString()
  }

  @UserFunction("geseq.rev_com")
  @Description("reverse and complement gene sequence")
  def reverseComplementGene(@Name("input") input: Array[Byte]): String = {
    val bytes = GeSeq.extractBbm(input)
    Decoding.convertFromBinaryArray(Complement.complement(Reverse.reverseDirect(bytes))).toString()
  }


}
