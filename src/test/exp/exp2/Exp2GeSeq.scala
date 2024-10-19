package exp.exp2

import org.cai.bbm.BBM
import org.cai.bbm.BBM.{findConsecutiveLowerCasePositions, removeAndRecord, removeAndRecordN}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test

/**
 * @author cai584770
 * @date 2024/10/17 13:31
 * @Version
 */
class Exp2GeSeq {
  private val filePath = Exp2Base.Path_10000000000

  @Test
  def reverse(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
    val (sup, ba) = BBM.bbm(agctSequence)

    val t1 = System.currentTimeMillis()
    val result: Array[Byte] = Reverse.reverseDirect(ba)
    println(s"reverse runtime:${System.currentTimeMillis() - t1}")
    println(areByteArraysEqual(Reverse.reverseDirect(result),ba))

  }

  @Test
  def complement(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
    val (sup, ba) = BBM.bbm(agctSequence)

    val t1 = System.currentTimeMillis()
    val result = Complement.complement(ba)
    println(s"complement:${System.currentTimeMillis() - t1}")
    println(areByteArraysEqual(Complement.complement(result),ba))

  }

  @Test
  def reverseANDcomplement(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
    val (sup, ba) = BBM.bbm(agctSequence)

    val t1 = System.currentTimeMillis()
    val result = Complement.complement(Reverse.reverseDirect(ba))
    println(s"reverse and complement runtime:${System.currentTimeMillis() - t1}")
    println(areByteArraysEqual(Complement.complement(Reverse.reverseDirect(result)),ba))


  }

  @Test
  def translate(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
    val (sup, ba) = BBM.bbm(agctSequence)

    val t1 = System.currentTimeMillis()
    val protein = TranslateTools.translate(ba)
    println(s"translate runtime:${System.currentTimeMillis() - t1}")


  }


  def areByteArraysEqual(arr1: Array[Byte], arr2: Array[Byte]): Boolean = {
    if (arr1.length != arr2.length) return false
    for (i <- arr1.indices) {
      if (arr1(i) != arr2(i)) return false
    }
    true
  }


}
