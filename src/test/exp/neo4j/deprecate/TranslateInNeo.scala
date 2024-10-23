package exp.neo4j.deprecate

import exp.neo4j.base.Neo4jConnect
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.util.Base64
import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/8/16 7:44
 * @Version
 */
class TranslateInNeo {

  @Test
  def translateInNeo(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())

    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val i = 43
      //      for (i <- 37 until 44) {// 1w,10w,100w,1000w,10,100,1000
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
        val byteArray = Base64.getDecoder.decode(base64String)
        val r = TranslateTools.translate(byteArray)

        val endTime = System.currentTimeMillis()
        println(endTime - startTime)
      }


      //      }
    }
    finally {
      session.close()
      driver.close()
    }
  }

  val geSeqFolder = "/home/cjw/program/ssm2be/data"

  @Test
  def geSeqInNeo4j(): Unit = {

    //    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())
    //
    //    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))
    //
    //    try {
    //      val files = new File(geSeqFolder)
    //      val allFiles: immutable.Seq[File] = TestTools.listAllFiles(files)
    //      var cypherQuery = ""
    //      for (elem <- allFiles) {
    //        val filePath = elem.toString
    //        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
    //        val sequence1 = FileNormalize.remove(sequence)
    //        val geSeq = GeSeq.fromSequence(sequence1)
    //
    //        val jsonString: String = Json.toJson(geSeq).toString()
    //
    //        val parsedGeSeqResult: JsResult[GeSeq] = Json.fromJson[GeSeq](Json.parse(jsonString))
    //        val t1 = System.currentTimeMillis()
    //        val geSeq1: GeSeq = parsedGeSeqResult.get
    //
    //        val bbm = geSeq1.getbbm
    //        val result: String = TranslateTools.translate(bbm)
    //        val t2 = System.currentTimeMillis()
    //        println(f"${filePath}:${t2 - t1}")
    //
    //      }
    //
    //      cypherQuery =
    //        """
    //          |match (n)
    //          |return n;
    //          |""".stripMargin
    //
    //      val result = session.run(cypherQuery)
    //
    //      while (result.hasNext) {
    //        val record = result.next()
    //        val node = record.get("n").asNode() // 获取节点对象
    //        println(s"Node: ${node.asMap()}") // 打印节点的所有属性
    //      }
    //    } finally {
    //      session.close()
    //      driver.close()
    //    }
  }


}
