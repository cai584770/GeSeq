package exp.mysql.connect;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author cai584770
 * @date 2024/10/15 15:19
 * @Version
 */
public class CreateTest {

    @Test // create geseq database
    public void createDBTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);

            stmt = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS geseq";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Test // create tair1000 table(blob & text)
    public void createTair1000TableTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();

            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            String createTable_str = "CREATE TABLE IF NOT EXISTS tair1000_str (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGTEXT, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_str);

            String createTable_blob = "CREATE TABLE IF NOT EXISTS tair1000_blob (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGBLOB, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_blob);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Test // create hg38 table(blob & text)
    public void createHg38TableTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();

            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            String createTable_str = "CREATE TABLE IF NOT EXISTS hg38_str (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGTEXT, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_str);

            String createTable_blob = "CREATE TABLE IF NOT EXISTS hg38_blob (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGBLOB, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_blob);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Test // create ce11 table(blob & text)
    public void createCE11TableTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();

            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            String createTable_str = "CREATE TABLE IF NOT EXISTS ce11_str (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGTEXT, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_str);

            String createTable_blob = "CREATE TABLE IF NOT EXISTS ce11_blob (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGBLOB, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_blob);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Test // create sacCer3 table(blob & text)
    public void createSacCer3TableTest() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();

            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            String createTable_str = "CREATE TABLE IF NOT EXISTS sacCer3_str (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGTEXT, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_str);

            String createTable_blob = "CREATE TABLE IF NOT EXISTS sacCer3_blob (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "information VARCHAR(255), " +
                    "sequence LONGBLOB, " +
                    "PRIMARY KEY (id))";
            stmt.executeUpdate(createTable_blob);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}
