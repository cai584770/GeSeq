package neo4j

import neo4j.config.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import scala.collection.immutable

/**
 * @author cai584770
 * @date 2024/8/16 23:13
 * @Version
 */
class Neo4jString {

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

        val cypherQuery =
          s"""
             CREATE (n:GeSeq {storage:'str',header: '$information', geseq: '$normalizeSequence'});
           """
        session.run(cypherQuery)
      }

      cypherQuery =
        """
          |match (n)
          |return n;
          |""".stripMargin

      val result = session.run(cypherQuery)

      while (result.hasNext) {
        val record = result.next()
        val node = record.get("n").asNode()
        println(s"Node: ${node.asMap()}")
      }
    } finally {
      session.close()
      driver.close()
    }
  }

}
