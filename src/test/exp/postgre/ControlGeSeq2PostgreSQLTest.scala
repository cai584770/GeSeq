package exp.postgre

import org.cai.file.processor.FilePath
import org.cai.file.{FileNormalize, FileProcess}
import org.cai.geseq.GeSeq
import org.cai.serialize.SerializeBase
import org.junit.jupiter.api.Test

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}
import scala.collection.mutable.ArrayBuffer

/**
 * @author cai584770
 * @date 2024/10/22 9:55
 * @Version
 */
class ControlGeSeq2PostgreSQLTest {
  private val inputFileDirPath = "/home/cjw/program/geseq/data"

  @Test
  def insertGeSeq(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    val allFilePaths: Array[String] = ArrayBuffer(
      "/home/cjw/program/geseq/data/1000.fa",
      "/home/cjw/program/geseq/data/100000.fa",
      "/home/cjw/program/geseq/data/10000000.fa"
    ).toArray


    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val sql = "INSERT INTO gene (information, my_geseq) VALUES (?, ROW(?,?,?,?,?,?))"
      statement = connection.prepareStatement(sql)

      for (path <- allFilePaths) {
        val (information, data) = FileProcess.getInformationAndSequence(path)
        val seq: GeSeq = GeSeq.fromSequence(FileNormalize.remove(data))

        try {
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
        }
      }
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

  @Test
  def insertString(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    val allFilePaths = FilePath.getAllFilePaths(inputFileDirPath).toArray

    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val sql = "INSERT INTO gene_str (information, sequence) VALUES (?, ?)"
      statement = connection.prepareStatement(sql)

      for (path <- allFilePaths) {
        val (information, data) = FileProcess.getInformationAndSequence(path.toString)
        val sequence =  FileNormalize.remove(data)

        try {
          statement.setString(1, information)
          statement.setString(2, sequence)

          statement.executeUpdate()
        } catch {
          case e: SQLException => e.printStackTrace()
        }
      }
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
