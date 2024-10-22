package exp.exp2

import org.cai.bbm.BBM
import org.cai.bbm.BBM.{findConsecutiveLowerCasePositions, removeAndRecord, removeAndRecordN}
import org.cai.file.processor.FilePath
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test

import java.util
import scala.util.control.Breaks.break

/**
 * @author cai584770
 * @date 2024/10/19 9:26
 * @Version
 */
class Exp2GeSeqBigFiles {
  private val filePath = "/data/cjw/geseq/10gb_geseq"

  @Test
  def reverse(): Unit = {
    val allFilePaths: Array[AnyRef] = FilePath.getAllFilePaths(filePath).toArray
    var totalTime: Double = 0
    var ba = new Array[Byte](0)
    var data = ""
    var sequence = ""
    var noLowerCaseSequence = ""
    var noNSequence = ""
    var agctSequence = ""

    for (path <- allFilePaths) {
      val (information, data) = FileProcess.getInformationAndSequence(path.toString)
      sequence = FileNormalize.remove(data)

      val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
      val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
      val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
      val (sup, ba) = BBM.bbm(agctSequence)

      val t1 = System.currentTimeMillis()
      val result = Reverse.reverseDirect(ba)
      totalTime += System.currentTimeMillis() - t1
      println(s"$path process end, reverse runtime:${System.currentTimeMillis() - t1}")

    }

    println(totalTime)


  }

  @Test
  def complement(): Unit = {
    val allFilePaths: Array[AnyRef] = FilePath.getAllFilePaths(filePath).toArray
    var totalTime: Double = 0
    var ba = new Array[Byte](0)
    var data = ""
    var sequence = ""
    var noLowerCaseSequence = ""
    var noNSequence = ""
    var agctSequence = ""

    for (path <- allFilePaths) {
      val (information, data) = FileProcess.getInformationAndSequence(path.toString)
      val sequence = FileNormalize.remove(data)

      val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
      val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
      val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
      val (sup, ba) = BBM.bbm(agctSequence)

      val t1 = System.currentTimeMillis()
      val result = Complement.complement(ba)
      totalTime += System.currentTimeMillis() - t1
      println(s"$path process end, complement runtime:${System.currentTimeMillis() - t1}")

    }

    println(totalTime)

  }

  @Test
  def reverseANDcomplement(): Unit = {
    val allFilePaths: Array[AnyRef] = FilePath.getAllFilePaths(filePath).toArray
    var totalTime: Double = 0
    var ba = new Array[Byte](0)
    var data = ""
    var sequence = ""
    var noLowerCaseSequence = ""
    var noNSequence = ""
    var agctSequence = ""

    for (path <- allFilePaths) {
      val (information, data) = FileProcess.getInformationAndSequence(path.toString)
      val sequence = FileNormalize.remove(data)

      val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
      val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
      val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
      val (sup, ba) = BBM.bbm(agctSequence)

      val t1 = System.currentTimeMillis()
      val result = Complement.complement(Reverse.reverseDirect(ba))
      totalTime += System.currentTimeMillis() - t1
      println(s"$path process end, reverse and complement runtime:${System.currentTimeMillis() - t1}")
    }

    println(totalTime)


  }

  @Test
  def translate(): Unit = {
    val allFilePaths: Array[AnyRef] = FilePath.getAllFilePaths(filePath).toArray
    var totalTime: Double = 0
    var ba = new Array[Byte](0)
    var data = ""
    var sequence = ""
    var noLowerCaseSequence = ""
    var noNSequence = ""
    var agctSequence = ""

    for (path <- allFilePaths) {
      val (information, data) = FileProcess.getInformationAndSequence(path.toString)
      val sequence = FileNormalize.remove(data)

      val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
      val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
      val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)
      val (sup, ba) = BBM.bbm(agctSequence)

      val t1 = System.currentTimeMillis()
      val protein = TranslateTools.translate(ba)
      totalTime += System.currentTimeMillis() - t1
      println(s"$path process end, translate runtime:${System.currentTimeMillis() - t1}")

    }

    println(totalTime)

  }


  def areByteArraysEqual(arr1: Array[Byte], arr2: Array[Byte]): Boolean = {
    if (arr1.length != arr2.length) return false
    for (i <- arr1.indices) {
      if (arr1(i) != arr2(i)) return false
    }
    true
  }



}
