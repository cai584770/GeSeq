package neo4j

import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test
import org.neo4j.driver._
import play.api.libs.json.{JsResult, Json}

import java.io.File
import scala.collection.immutable

/**
 * @author cai584770
 * @date 2024/8/16 7:44
 * @Version
 */
class TranslateInNeo {
  val uri = "bolt://localhost:7687"
  val username = "neo4j"
  val password = "neo4j"

  @Test
  def translateInNeo(): Unit = {
    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())

    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      for (i <- 0 until 12) {
        val cypherQuery =
          s"""
             |MATCH (n:GeSeq)
             |WHERE id(n) = $i
             |WITH n.geseq AS geseqData
             |RETURN translateDNA(geseqData) AS result;
             |""".stripMargin

        val startTime = System.currentTimeMillis()
        val result = session.run(cypherQuery)
        val endTime = System.currentTimeMillis()

        println(endTime - startTime)

        //        while (result.hasNext) {
        //          val record = result.next()
        //          println(record.get("result").asString())
        //        }
      }
    }
    finally {
      session.close()
      driver.close()
    }
  }

  val geSeqFolder = "/home/cjw/program/ssm2be/data"

  @Test
  def geSeqInNeo4j(): Unit = {

    val driver: Driver = GraphDatabase.driver(uri, AuthTokens.none())

    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val files = new File(geSeqFolder)
      val allFiles: immutable.Seq[File] = TestTools.listAllFiles(files)
      var cypherQuery = ""
      for (elem <- allFiles) {
        val filePath = elem.toString
        val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
        val sequence1 = FileNormalize.remove(sequence)
        val geSeq = GeSeq.fromSequence(sequence1)

        val jsonString: String = Json.toJson(geSeq).toString()

        val parsedGeSeqResult: JsResult[GeSeq] = Json.fromJson[GeSeq](Json.parse(jsonString))
        val t1 = System.currentTimeMillis()
        val geSeq1: GeSeq = parsedGeSeqResult.get

        val bbm = geSeq1.getbbm
        val result: String = TranslateTools.translate(bbm)
        val t2 = System.currentTimeMillis()
        println(f"${filePath}:${t2 - t1}")

      }

      cypherQuery =
        """
          |match (n)
          |return n;
          |""".stripMargin

      val result = session.run(cypherQuery)

      while (result.hasNext) {
        val record = result.next()
        val node = record.get("n").asNode() // 获取节点对象
        println(s"Node: ${node.asMap()}") // 打印节点的所有属性
      }
    } finally {
      session.close()
      driver.close()
    }
  }


}
