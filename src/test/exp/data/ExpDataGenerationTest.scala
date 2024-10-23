package exp.data;

import org.cai.bbm.BBM.{findConsecutiveLowerCasePositions, removeAndRecord, removeAndRecordN}
import org.cai.file.{FileNormalize, FileProcess}
import org.junit.jupiter.api.Test

import java.io.{BufferedReader, File, FileInputStream, FileReader, IOException, PrintWriter}
import java.nio.file.{Files, Paths}
import java.util.Random
import scala.io.Source;

/**
 * @author cai584770
 * @date 2024/10/17 9:30
 * @Version
 */
class ExpDataGenerationTest {
//  val hg38_chr1 = "F:\\GeneData\\geseq\\orignal\\HG38\\chr1.fa"
  val hg38_chr1 = "/home/cjw/program/data/human/hg38/chr1.fa"

  @Test // kb mb
  def create(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(hg38_chr1)
    val sequence = FileNormalize.remove(data)
    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)

    println(agctSequence.length)
//    1000000000000 10000000000 100000000 1000000 10000 100

    val lengths = List(100, 1000, 10000, 100000 ,1000000, 10000000)

    extractAndSave(agctSequence, lengths)
  }



  @Test
  def createBigScalaDataSet():Unit={
    val (information, data) = FileProcess.getInformationAndSequence("/home/cjw/program/geseq/data/100000000.fa")
    val sequence = FileNormalize.remove(data)
    val (noLowerCaseSequence, lowerCaseList) = findConsecutiveLowerCasePositions(sequence)
    val (noNSequence, nCaseList) = removeAndRecordN(noLowerCaseSequence)
    val (agctSequence, otherCaseList) = removeAndRecord(noNSequence)

    println(agctSequence.length)
    val times = 10000
    for (i <- 1001 to times) {
      writeToFile1(s"/data/cjw/geseq/10gb_geseq/${i}.fa", agctSequence, 1)
    }
  }


  @Test
  def mathB(): Unit = {
//    1 tb = 1 * 1024 gb = 1 * 1024 * 1024 mb = 1 * 1024 * 1024 kb


    val kb = 1024L
    val mb = 1048576L
    val gb = 1073741824L
    val tb = 1099511627776L


    println(kb)
    println(mb)
    println(gb)
    println(tb)
    println(gb / (1073741824L * 128L))
    println(tb / (1073741824L * 128L))
    println((1048576L * 1024L * 1024L) == 1099511627776L )
  }

  def writeToFile1(filePath: String, str: String, times: Int): Unit = {
    val writer = new PrintWriter(filePath)
    writer.println(">tb")
    for (i <- 1 to times) {
      writer.println(str)
      println(s"time $i write!")
    }
    writer.close()
  }

  def getFileSize(filePath: String): Long = {
    var size = 0
    try {
      val fis = new FileInputStream(filePath)
      try {
        val buffer = new Array[Byte](65536)
        var bytesRead = 0
        while ( {
          (bytesRead = fis.read(buffer)) != -1
        }) size += bytesRead
      } catch {
        case e: IOException =>
          e.printStackTrace()
      } finally if (fis != null) fis.close()
    }
    size
  }

  def writeToFile(filePath: String, sequence: String, frequency: Int): Unit = {
    val completeSequence = sequence * frequency

    val sequenceLength = sequence.length * frequency
    val fileName = s"$filePath/$sequenceLength.fa"
    createDirectoriesIfNotExist(fileName)
    val file = new File(fileName)
    val writer = new PrintWriter(file)

    writer.println(s">${sequenceLength}")
    writer.println(completeSequence)

    writer.close()

    println(s"Saved sequence length $sequenceLength to $fileName, ${completeSequence.length}")
  }

  def extractAndSave(sequence: String, lengths: List[Int]): Unit = {
    val rand = new Random

    lengths.foreach {
      length =>
        if (length <= sequence.length) {
          val startPos = rand.nextInt(sequence.length - length + 1)
          val subSeq = sequence.substring(startPos, startPos + length)

          val fileName = s"/home/cjw/program/geseq/data/$length.fa"
          createDirectoriesIfNotExist(fileName)
          val file = new File(fileName)
          val writer = new PrintWriter(file)

          writer.println(s">$length")
          writer.println(subSeq)

          writer.close()
          println(s"Saved sequence of length $length to $fileName")
        } else {
          println(s"Requested length $length is greater than the available sequence length.")
        }
    }
  }

  def createDirectoriesIfNotExist(filePath: String): Unit = {
    val directory = Paths.get(filePath).getParent
    if (Files.notExists(directory)) {
      Files.createDirectories(directory)
    }
  }

}
