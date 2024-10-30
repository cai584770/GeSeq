package exp.postgre.exp3

import exp.postgre.base.PostgreSQLBase
import org.cai.tools.convert.{Complement, Reverse}
import org.cai.tools.translate.{TranslateProtein, TranslateTools}
import org.junit.jupiter.api.Test

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}

/**
 * @author cai584770
 * @date 2024/10/23 13:05
 * @Version
 */
class StringExpInPostgreSQL {
  //  1 | 100
  //  2 | 10000
  //  3 | 1000000
  //  4 | 100000000
  //  5 | 1000
  //  6 | 100000
  //  7 | 10000000
  private val sql ="""
                    SELECT sequence
                    FROM gene_str
                    WHERE id = 7;
                  """
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
          val sequence = resultSet.getString("sequence")
          val result = TranslateProtein.translateString(sequence)

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
          val sequence: String = resultSet.getString("sequence")
          val result = Reverse.reverseString(sequence)

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
          val sequence = resultSet.getString("sequence")
          val result = Complement.complementString(sequence)

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
          val sequence = resultSet.getString("sequence")
          val result = Complement.complementString(Reverse.reverseString(sequence))

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
}
