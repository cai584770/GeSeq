package neo4j

import neo4j.config.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import scala.collection.JavaConverters._
import scala.collection.immutable
import org.neo4j.driver.{Session, Values}

import java.util

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
      for (elem <- allFiles) {
        val filePath = elem.toString
        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
        val normalizeSequence = FileNormalize.remove(sequence)

        val bbm: Array[Byte] = GeSeq.fromSequence(normalizeSequence).getbbm


        val cypherQuery =
          """
            |CREATE (n:GeSeq
            |{storage:'lii',
            |geseq: $par});
            |""".stripMargin

        val integerList: java.util.List[Integer] = bbm.map(_.toInt).map(Int.box).toList.asJava

        val parameters: util.Map[String, Object] = Map("par" -> integerList.asInstanceOf[Object]).asJava

        val result = session.run(cypherQuery, parameters)
      }

    } finally {
      session.close()
      driver.close()
    }
  }


}
