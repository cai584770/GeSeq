package neo4j

import neo4j.config.Neo4jConnect
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.junit.jupiter.api.Test
import org.neo4j.driver._

import scala.collection.JavaConverters._

/**
 * @author cai584770
 * @date 2024/8/23 10:52
 * @Version
 */
class Neo4jMap {

  @Test
  def mapImport(): Unit = {
    val driver: Driver = GraphDatabase.driver(Neo4jConnect.uri, AuthTokens.none())
    val session: Session = driver.session(SessionConfig.forDatabase("neo4j"))

    try {
      val filePath = "/home/cjw/program/ssm2be/data/1000.fa"
      val (information, sequence) = FileProcess.getInformationAndSequence(filePath)
      val normalizeSequence = FileNormalize.remove(sequence)

      val seq: GeSeq = GeSeq.fromSequence(normalizeSequence)

      val byteArray: Array[Byte] = seq.toByteArray

      println(byteArray.length)

      val cypherQuery =
        """
          |CREATE (n:GeSeq {
          |  storage: $storage,
          |  information: $information,
          |  geseq: $geseq
          |})
          """.stripMargin

      val parameters = Map(
        "storage" -> "byteArray".asInstanceOf[Object],
        "information" -> information.asInstanceOf[Object],
        "geseq" -> byteArray.asInstanceOf[Object]
      ).asJava

      session.run(cypherQuery,parameters)
    } finally {
      session.close()
      driver.close()
    }
  }


}
