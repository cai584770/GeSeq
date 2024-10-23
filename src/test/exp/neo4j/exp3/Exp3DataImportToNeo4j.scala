package exp.neo4j.exp3

import exp.neo4j.base.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import scala.collection.JavaConverters._
import scala.collection.immutable

/**
 * @author cai584770
 * @date 2024/10/23 16:23
 * @Version
 */
class Exp3DataImportToNeo4j {

  @Test
  def geseqImport(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val files = new File(DataSet.geSeqFolder)
      val allFiles: immutable.Seq[File] = TestTools.listAllFiles(files)
      for (elem <- allFiles) {
        val filePath = elem.toString
        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
        val normalizeSequence = FileNormalize.remove(sequence)
        val seq: GeSeq = GeSeq.fromSequence(normalizeSequence)
        val byteArray: Array[Byte] = seq.toByteArray

        val cypherQuery =
          """
            |CREATE (n:Gene_geseq {
            |  storage: 'geseq',
            |  information: $information,
            |  geseq: $geseq
            |})
          """.stripMargin

        val parameters = Map(
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
  def stringImport(): Unit = {
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

        cypherQuery =
          s"""
           CREATE (n:Gene_str{storage:'str',header: '$information',geseq: '$normalizeSequence'});
           """
        session.run(cypherQuery)
      }
    } finally {
      session.close()
      driver.close()
    }
  }

}
