package exp.neo4j.exp3

import exp.neo4j.base.Neo4jConnect
import org.cai.geseq.GeSeq
import org.cai.tools.convert.{Complement, Reverse}
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/10/23 16:31
 * @Version
 */
class Exp3CypherInNeo4j {
//  Node ID: 7 , Storage: str, Information: 100
//  Node ID: 8 , Storage: str, Information: 10000
//  Node ID: 9 , Storage: str, Information: 1000000
//  Node ID: 10, Storage: str, Information: 100000000
//  Node ID: 11, Storage: str, Information: 1000
//  Node ID: 12, Storage: str, Information: 100000
//  Node ID: 13, Storage: str, Information: 10000000

  private val id = 7

  @Test
  def reverseCypher(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |match (n) where id(n) = $nodeId
          |with n.geseq as seq
          |with reverse(seq) as sequence
          |return sequence
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val result = session.run(cypherQuery, parameters)

      val endTime = System.currentTimeMillis() - startTime
      println(endTime - startTime)

    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def complementCypher(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |match (n) where id(n) = $nodeId
          |with n.geseq as sequence
          |with replace(sequence, 'A', 't') as at_seq
          |with replace(at_seq, 'T', 'a') as ta_seq
          |with replace(ta_seq, 'C', 'g') as cg_seq
          |with replace(cg_seq, 'G', 'c') as gc_seq
          |return toUpper(gc_seq)
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val result = session.run(cypherQuery, parameters)

      val endTime = System.currentTimeMillis() - startTime
      println(endTime - startTime)

    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def rev_comCypher(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = id
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |match (n) where id(n) = $nodeId
          |with n.geseq as seq
          |with reverse(seq) as re_seq
          |with replace(re_seq, 'A', 't') as at_seq
          |with replace(at_seq, 'T', 'a') as ta_seq
          |with replace(ta_seq, 'C', 'g') as cg_seq
          |with replace(cg_seq, 'G', 'c') as gc_seq
          |return toUpper(gc_seq)
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava
      val result = session.run(cypherQuery, parameters)

      val endTime = System.currentTimeMillis() - startTime
      println(endTime - startTime)
    } finally {
      session.close()
      driver.close()
    }
  }


}
