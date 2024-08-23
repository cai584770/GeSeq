package neo4j

import neo4j.config.Neo4jConnect
import org.cai.geseq.GeSeq
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.Decoding
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
 * @author cai584770
 * @date 2024/8/16 7:44
 * @Version
 */
class ReverseAndComplementInNeo {


  @Test
  def rev_com(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = 7
      val l1 = System.currentTimeMillis()
      val cypherQuery =
        """
          |MATCH (n:GeSeq)
          |where id(n) = $nodeId
          |RETURN n.geseq AS geseq
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava
      val result = session.run(cypherQuery,parameters)
      val l2 = System.currentTimeMillis()

      while (result.hasNext) {

        val record = result.next()
        val byteArray = record.get("geseq").asByteArray()
        val l3 = System.currentTimeMillis()

        val geSeq = GeSeq.fromByteArray(byteArray)
        val l4 = System.currentTimeMillis()

        val re = Complement.complement(Reverse.reverseDirect(GeSeq.extractBbm(byteArray)))
        val l5 = System.currentTimeMillis()

        println(s"l2-l1:${l2-l1}")
        println(s"l3-l2:${l3-l2}")
        println(s"l4-l3:${l4-l3}")
        println(s"l5-l4:${l5-l4}")
        println(s"l5-l1:${l5-l1}")

        println(geSeq.sequenceLength)
        println(re.length)

      }
    } finally {
      session.close()
      driver.close()
    }
  }

  @Test
  def rev_comString(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      //      for (i <- 49 until 56) {  // 1w,10w,100w,1000w,10,100,1000
      val i = 1
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |match (n) where id(n) = $nodeId
          |with n.geseq as seq
          |with replace(seq, 'A', 't') as at_seq
          |with replace(at_seq, 'T', 'a') as ta_seq
          |with replace(ta_seq, 'C', 'g') as cg_seq
          |with replace(cg_seq, 'G', 'c') as gc_seq
          |return toUpper(gc_seq)
          |""".stripMargin
      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val result = session.run(cypherQuery, parameters)

      val endTime = System.currentTimeMillis()
      println(endTime - startTime)

      //      }
    } finally {
      session.close()
      driver.close()
    }
  }


}
