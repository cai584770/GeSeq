package neo4j

import neo4j.config.Neo4jConnect
import org.cai.tools.convert.{Complement, Convert}
import org.cai.tools.translate.{Decoding, TranslateTools}
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.util.Base64
import scala.collection.JavaConverters._

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
      for (i <- 37 until 49) {
        val startTime = System.currentTimeMillis()
        val cypherQuery =
          """
            |MATCH (n:GeSeq)
            |WHERE id(n) = $nodeId
            |RETURN n.geseq AS geseqData;
            |""".stripMargin
        val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

        val result = session.run(cypherQuery, parameters)

        while (result.hasNext) {
          val record = result.next()
          val base64String = record.get("geseqData").asString()
          val byteArray: Array[Byte] = Base64.getDecoder.decode(base64String)
          val r = Complement.complement(byteArray)
          Decoding.convertFromBinaryArray(r).toString()
          val endTime = System.currentTimeMillis()
          println(endTime - startTime)
        }

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
      val i = 49
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |match (n) where id(n) = $nodeId
          |with n.geseq as seq
          |return reverse(seq) as re_seq
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
