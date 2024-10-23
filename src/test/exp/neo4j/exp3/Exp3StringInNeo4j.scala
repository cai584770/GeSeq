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
 * @date 2024/10/23 16:31
 * @Version
 */
class Exp3StringInNeo4j {
//  Node ID: 7 , Storage: str, Information: 100
//  Node ID: 8 , Storage: str, Information: 10000
//  Node ID: 9 , Storage: str, Information: 1000000
//  Node ID: 10, Storage: str, Information: 100000000
//  Node ID: 11, Storage: str, Information: 1000
//  Node ID: 12, Storage: str, Information: 100000
//  Node ID: 13, Storage: str, Information: 10000000

  private val id = 13

  @Test
  def reverse(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val cypherQuery =
        """
          |MATCH (n:Gene_str)
          |where id(n) = $nodeId
          |RETURN n.geseq AS sequence
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val sequence = record.get("sequence").toString

        val re = Reverse.reverseString(sequence)
        val l2 = System.currentTimeMillis() - l1
        println(s"reverse time: $l2")
        println(sequence.length)

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
          |MATCH (n:Gene_str)
          |where id(n) = $nodeId
          |RETURN n.geseq AS sequence
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val sequence = record.get("sequence").toString
        val re = Complement.complementString(sequence)

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
          |MATCH (n:Gene_str)
          |where id(n) = $nodeId
          |RETURN n.geseq AS sequence
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val sequence = record.get("sequence").toString
        val re = Complement.complementString(Reverse.reverseString(sequence))

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
          |MATCH (n:Gene_str)
          |where id(n) = $nodeId
          |RETURN n.geseq AS sequence
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val l1 = System.currentTimeMillis()
      val result = session.run(cypherQuery, parameters)

      while (result.hasNext) {
        val record = result.next()
        val sequence = record.get("sequence").toString

//        val re = TranslateTools.translate(sequence)

        val l2 = System.currentTimeMillis() - l1
        println(s"translate time: $l2")

      }
    } finally {
      session.close()
      driver.close()
    }
  }


}
