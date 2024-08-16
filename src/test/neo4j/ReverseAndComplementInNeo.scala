package neo4j

import neo4j.config.Neo4jConnect
import org.cai.tools.convert.{Complement, Convert}
import org.cai.tools.translate.{Decoding, TranslateTools}
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.util
import java.util.Base64
import scala.collection.JavaConverters._
import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`

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
      //      for (i <- 73 until 85) {
      val i = 76
      val startTime = System.currentTimeMillis()
      val cypherQuery =
        """
          |MATCH (n:GeSeq)
          |WHERE id(n) = $nodeId
          |RETURN n.geseq AS geseqData;
          |""".stripMargin

      val parameters: java.util.Map[String, Object] = Map("nodeId" -> i.asInstanceOf[Object]).asJava

      val result = session.run(cypherQuery, parameters)

      val integerList: List[Integer] = result.single().get("geseqData").asList((value: org.neo4j.driver.Value) => value.asInt(): Integer).asScala.toList
      val invertedList: List[Int] = integerList.map(i => ~i)


      //      val byteArray: Array[Byte] = integerList.map(_.toByte).toArray
//
//      Convert.convertDirect(byteArray)

      val end = System.currentTimeMillis()
      println(end - startTime)

      //      }
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
