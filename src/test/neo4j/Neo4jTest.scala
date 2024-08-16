package neo4j

import org.cai.bbm.BBM
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.BioSequenceEnum.DNA
import org.cai.geseq.{BioSequenceEnum, GeSeq}
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import java.util
import scala.collection.immutable
import scala.collection.JavaConverters._
import java.util.{HashMap, Map => JMap}
/**
 * @author cai584770
 * @date 2024/8/15 15:44
 * @Version
 */
class Neo4jTest {

  val uri = "bolt://localhost:7687"
  val username = "neo4j"
  val password = "neo4j"

  @Test
  def clearNeo(): Unit = {
    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val cypherQuery =
        """
          |MATCH (n)
          |DETACH DELETE n;
          |""".stripMargin

      session.run(cypherQuery)
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def matchNeo4j(): Unit = {
    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val cypherQuery =
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

  @Test
  def geSeqInNeo4j(): Unit = {
    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())

    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val files = new File(DataSet.geSeqFolder)
      val allFiles: immutable.Seq[File] = listAllFiles(files)
      var cypherQuery = ""
      for (elem <- allFiles) {
        val filePath = elem.toString
        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
        val normalizeSequence = FileNormalize.remove(sequence)

        val mapN: Map[String, Any] = GeSeq.fromSequence(normalizeSequence).toNeo4jMap

        val parameters: util.HashMap[String, Object] = new util.HashMap[String,Object]()

        mapN.foreach { case (key, value) => parameters.put(key, value.asInstanceOf[Object])
        }


        val cypherQuery =
          s"""
             CREATE (n:GeSeq {header: '$information', geseq: $parameters});
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



  def listAllFiles(dir: File): List[File] = {
    val files = dir.listFiles
    val folders = files.filter(_.isDirectory).toList
    val folderFiles = folders.flatMap(listAllFiles)
    files.filter(_.isFile).toList ++ folderFiles
  }
}
