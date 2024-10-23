package exp.postgre

import org.cai.geseq.GeSeq
import org.cai.serialize.DeSerializeBase
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.TranslateTools
import org.junit.jupiter.api.Test

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}

/**
 * @author cai584770
 * @date 2024/10/22 16:16
 * @Version
 */
class ExpInPostgreSQL {
  private val sql ="""
                    SELECT (my_geseq).sequence
                    FROM gene
                    WHERE id = 7
                  """
//  1 | 100
//  2 | 10000
//  3 | 1000000
//  4 | 100000000
//  5 | 1000
//  6 | 100000
//  7 | 10000000

  @Test
  def reverse(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val t1 = System.currentTimeMillis()

      val pstmt = connection.prepareStatement(sql)
      val resultSet = pstmt.executeQuery()

      try {
        while (resultSet.next()) {
          val sequenceBytes: Array[Byte] = resultSet.getBytes("sequence")
          val result = Reverse.reverseDirect(sequenceBytes)

          val runtime = System.currentTimeMillis() - t1
          println(s"reverse: $runtime")

        }
      } finally {
        if (resultSet != null) resultSet.close()
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
  def complement(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val t1 = System.currentTimeMillis()

      val pstmt = connection.prepareStatement(sql)
      val resultSet = pstmt.executeQuery()

      try {
        while (resultSet.next()) {
          val sequenceBytes: Array[Byte] = resultSet.getBytes("sequence")
          val result = Complement.complement(sequenceBytes)

          val runtime = System.currentTimeMillis() - t1
          println(s"complement: $runtime")


        }
      } finally {
        if (resultSet != null) resultSet.close()
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
  def rev_com(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val t1 = System.currentTimeMillis()

      val pstmt = connection.prepareStatement(sql)
      val resultSet = pstmt.executeQuery()

      try {
        while (resultSet.next()) {
          val sequenceBytes: Array[Byte] = resultSet.getBytes("sequence")
          val result = Complement.complement(Reverse.reverseDirect(sequenceBytes))

          val runtime = System.currentTimeMillis() - t1
          println(s"reverse and complement: $runtime")


        }
      } finally {
        if (resultSet != null) resultSet.close()
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
  def translate(): Unit = {
    var connection: Connection = null
    var statement: PreparedStatement = null

    try {
      connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)
      val t1 = System.currentTimeMillis()

      val pstmt = connection.prepareStatement(sql)
      val resultSet = pstmt.executeQuery()

      try {
        while (resultSet.next()) {
          val sequenceBytes: Array[Byte] = resultSet.getBytes("sequence")
          val result = TranslateTools.translate(sequenceBytes)

          val runtime = System.currentTimeMillis() - t1
          println(s"translate: $runtime")

        }
      } finally {
        if (resultSet != null) resultSet.close()
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
