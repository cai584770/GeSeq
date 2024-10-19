package exp.neo4j

import exp.neo4j.config.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.cai.tools.convert.Reverse
import org.cai.tools.translate.Decoding
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import scala.collection.JavaConverters._
import scala.collection.immutable

/**
 * @author cai584770
 * @date 2024/8/17 0:36
 * @Version
 */
class Neo4jArray {

  @Test
  def arrayByteImport(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val files = new File(DataSet.geSeqFolder)
      val allFiles: immutable.Seq[File] = TestTools.listAllFiles(files)
      var cypherQuery = ""
      for (elem <- allFiles) {
        val filePath = elem.toString
        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
        val normalizeSequence = FileNormalize.remove(sequence)

        val seq: GeSeq = GeSeq.fromSequence(normalizeSequence)

        val byteArray: Array[Byte] = seq.toByteArray

        val cypherQuery =
          """
            |CREATE (n:GeSeq {
            |  storage: $storage,
            |  information: $information,
            |  geseq: $geseq
            |})
          """.stripMargin

        val parameters = Map(
          "storage" -> "byteArray".asInstanceOf[Object],
          "information" -> information.asInstanceOf[Object],
          "geseq" -> byteArray.asInstanceOf[Object]
        ).asJava

        session.run(cypherQuery, parameters)
      }
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def arrayByteExport: Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val l = System.currentTimeMillis()
      val cypherQuery = "MATCH (n:GeSeq) RETURN n.geseq AS geseq"
      val result = session.run(cypherQuery)

      while (result.hasNext) {
        val record = result.next()
        val byteArray = record.get("geseq").asByteArray()
        val geSeq = GeSeq.fromByteArray(byteArray)

        val re = Decoding.convertFromBinaryArray(Reverse.reverseDirect(geSeq.sequence))
        //        println(s"Retrieved GeSeq: ${geSeq.getbbm}")
        //        println(s"Retrieved GeSeq: ${geSeq.nBase}")
        //        println(s"Retrieved GeSeq: ${Decoding.convertFromBinaryArray(geSeq.sequence)}")
        println(System.currentTimeMillis() - l)
        println(geSeq.sequenceLength)
      }
    } finally {
      session.close()
      driver.close()
    }

  }

}
