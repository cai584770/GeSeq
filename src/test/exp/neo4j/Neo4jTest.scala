package exp.neo4j

import exp.neo4j.config.{DataSet, Neo4jConnect, TestTools}
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.util.HashMap
import org.neo4j.driver.Values

import java.io.File
import java.util
import scala.collection.immutable
/**
 * @author cai584770
 * @date 2024/8/15 15:44
 * @Version
 */
class Neo4jTest {

  @Test
  def clearNeo(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
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
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
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

        val mapN: Map[String, Any] = GeSeq.fromSequence(normalizeSequence).toNeo4jMap

        val parameters: util.HashMap[String, Object] = new util.HashMap[String,Object]()

        mapN.foreach { case (key, value) =>
          println(f"$key --- ${value.asInstanceOf[Object]}")
          parameters.put(key, value.asInstanceOf[Object])
        }


        val cypherQuery =
          s"""
             CREATE (n:GeSeq {header: '$information', geseq: '$parameters'});
           """

//        val query = "CREATE (n:Gene {sequence: $sequence, nBase: $nBase, lowercase: $lowercase, })"
//        session.run(query, parameters)
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
