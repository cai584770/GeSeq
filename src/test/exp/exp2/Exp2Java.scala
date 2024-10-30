package exp.exp2

import org.cai.file.{FileNormalize, FileProcess}
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.TranslateProtein
import org.junit.jupiter.api.Test

/**
 * @author cai584770
 * @date 2024/10/23 20:46
 * @Version
 */
class Exp2Java {

//100000000.fa
//1000000.fa
//10000.fa
//100.fa

  private val filePath = "/home/cjw/program/geseq/data/1000.fa"

  @Test
  def reverse(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val startTime = System.currentTimeMillis()
    val result = Reverse.reverseString(sequence)
    val totalTime = System.currentTimeMillis()-startTime

    println(s"reverse time: $totalTime")

  }

  @Test
  def complement(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val startTime = System.currentTimeMillis()
    val result = Complement.complementString(sequence)
    val totalTime = System.currentTimeMillis() - startTime

    println(s"complement time: $totalTime")

  }

  @Test
  def reverse_complement(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val startTime = System.currentTimeMillis()
    val result = Complement.complementString(Reverse.reverseString(sequence))
    val totalTime = System.currentTimeMillis() - startTime

    println(s"reverse and complement time: $totalTime")
  }

  @Test
  def translate(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(filePath)
    val sequence = FileNormalize.remove(data)

    val startTime = System.currentTimeMillis()
    val result = TranslateProtein.translateString(sequence)
    val totalTime = System.currentTimeMillis() - startTime

    println(s"translate time: $totalTime")
  }

}
