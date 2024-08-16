package neo4j

import neo4j.config.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import java.util.Base64
import scala.collection.immutable

/**
 * @author cai584770
 * @date 2024/8/17 0:36
 * @Version
 */
class Neo4jArray {

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

        val bbm: Array[Byte] = GeSeq.fromSequence(normalizeSequence).getbbm

        val base64String = Base64.getEncoder.encodeToString(bbm)

        val cypherQuery =
          s"""
           CREATE (n:GeSeq {storage:'ab',header: '$information', geseq: '$base64String'});
         """
        session.run(cypherQuery)
      }

    } finally {
      session.close()
      driver.close()
    }
  }


}
