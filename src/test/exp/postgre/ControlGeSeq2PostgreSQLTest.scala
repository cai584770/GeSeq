package exp.postgre

import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.cai.serialize.SerializeBase
import org.junit.jupiter.api.Test

import java.sql._
/**
 * @author cai584770
 * @date 2024/10/22 9:55
 * @Version
 */
class ControlGeSeq2PostgreSQLTest {
  private val inputFilePath = ""

  @Test
  def insert(): Unit = {
    val (information, data) = FileProcess.getInformationAndSequence(inputFilePath)
    val seq: GeSeq = GeSeq.fromSequence(FileNormalize.remove(data))

    val url = PostgreSQLBase.url
    val user = PostgreSQLBase.user
    val password = PostgreSQLBase.password
    var connection: Connection = null
    var statement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(url, user, password)
      val sql = "INSERT INTO my_table (information, my_geseq) VALUES (?, ROW(?,?,?,?,?,?))"
      statement = connection.prepareStatement(sql)

      statement.setString(1, information)
      statement.setBytes(2, seq.sequence)
      statement.setBytes(3, SerializeBase.serializeListOfPairs(seq.lowercase))
      statement.setBytes(4, SerializeBase.serializeListOfPairs(seq.nBase))
      statement.setBytes(5, SerializeBase.serializeListOfPairWithString(seq.otherBASE))
      statement.setLong(6, seq.sequenceLength)
      statement.setLong(7, seq.nucleotidesLength)

      statement.executeUpdate()
    } catch {
      case e: SQLException => e.printStackTrace()
    } finally {
      if (statement != null) try statement.close() catch {
        case e: SQLException => e.printStackTrace()
      }
      if (connection != null) try connection.close() catch {
        case e: SQLException => e.printStackTrace()
      }
    }
  }


}
