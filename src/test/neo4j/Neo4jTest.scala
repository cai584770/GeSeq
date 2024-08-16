package neo4j

import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import java.io.File
import scala.collection.immutable

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

        val geSeqMap: Map[String, Any] = GeSeq.fromSequence(normalizeSequence).toMap

        val cypherQuery =
          s"""
             CREATE (n:GeSeq {header: '$information', geseq: $geSeqMap});
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

  @Test
  def importNeo4j(): Unit = {
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

        val geSeqMap: Map[String, Any] = GeSeq.convertGeSeqToNeo4jFormat(GeSeq.fromSequence(normalizeSequence))

        val cypherQuery =
          s"""
             CREATE (n:GeSeq {header: '$information', geseq: $geSeqMap});
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


  //  @Test
  //  def geSeq(): Unit = {
  //    val geSeq = GeSeq(
  //      bbm = Array[Byte](1, 2, 3),
  //      lowercase = List((1, 2)),
  //      nBase = List((3, 4)),
  //      otherBASE = List((5, "A")),
  //      sequenceLength = 100L,
  //      nucleotidesLength = 50L
  //    )
  //
  //    val jsonResult: Try[String] = Try {
  //      val json = Json.toJson(geSeq)
  //      println(s"JSON Result: $json") // 打印 JSON 对象以帮助调试
  //      json.toString()
  //    }
  //
  //    jsonResult match {
  //      case Success(jsonString) =>
  //        val cypherQuery =
  //          s"""
  //           CREATE (n:GeSeq {data: '$jsonString'})
  //         """
  //        println(cypherQuery)
  //      case Failure(exception) =>
  //        println(s"Error converting to JSON: ${exception.getMessage}")
  //        exception.printStackTrace() // 打印堆栈信息以帮助调试
  //    }
  //  }

  def listAllFiles(dir: File): List[File] = {
    val files = dir.listFiles
    val folders = files.filter(_.isDirectory).toList
    val folderFiles = folders.flatMap(listAllFiles)
    files.filter(_.isFile).toList ++ folderFiles
  }
}
