package exp.neo4j.exp3

import exp.neo4j.base.Neo4jConnect
import org.cai.geseq.GeSeq
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/10/23 16:30
 * @Version
 */
class Exp3GeSeqInNeo4j {
//Node ID: 0, Storage: geseq, Information: 100
//Node ID: 1, Storage: geseq, Information: 10000
//Node ID: 2, Storage: geseq, Information: 1000000
//Node ID: 3, Storage: geseq, Information: 100000000
//Node ID: 4, Storage: geseq, Information: 1000
//Node ID: 5, Storage: geseq, Information: 100000
//Node ID: 6, Storage: geseq, Information: 10000000
  private val id = 6

  @Test
  def reverse(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val cypherQuery =
        """
          |MATCH (n:Gene_geseq)
          |where id(n) = $nodeId
          |RETURN n.geseq AS geseq
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val byteArray: Array[Byte] = record.get("geseq").asByteArray()

        val re = Reverse.reverseDirect(GeSeq.extractBbm(byteArray))
        val l2 = System.currentTimeMillis()-l1
        println(s"reverse time: $l2")
      }
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def complement(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val cypherQuery =
        """
          |MATCH (n:Gene_geseq)
          |where id(n) = $nodeId
          |RETURN n.geseq AS geseq
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val geseq = record.get("geseq").asByteArray()
        val re = Complement.complement(GeSeq.extractBbm(geseq))

        val l2 = System.currentTimeMillis() - l1
        println(s"complement time: $l2")
      }
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def rev_com(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val cypherQuery =
        """
          |MATCH (n:Gene_geseq)
          |where id(n) = $nodeId
          |RETURN n.geseq AS geseq
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val geSeq = record.get("geseq").asByteArray()
        val re = Complement.complement(Reverse.reverseDirect(GeSeq.extractBbm(geSeq)))

        val l2 = System.currentTimeMillis() - l1
        println(s"reverse and complement time: $l2")

      }
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def translate(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val cypherQuery =
        """
          |MATCH (n:Gene_geseq)
          |where id(n) = $nodeId
          |RETURN n.geseq AS geseq
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val geSeq = record.get("geseq").asByteArray()
        val re = TranslateTools.translate(GeSeq.extractBbm(geSeq))

        val l2 = System.currentTimeMillis() - l1
        println(s"translate time: $l2")

      }
    } finally {
      session.close()
      driver.close()
    }
  }

}
