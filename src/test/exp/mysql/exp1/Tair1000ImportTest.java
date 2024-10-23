package exp.mysql.exp1;

import exp.mysql.base.TestBase;
import org.cai.db.mysql.DML.Insert;
import org.cai.file.processor.FilePath;
import org.cai.file.processor.FileProcessor;
import org.cai.file.type.FASTA;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/15 20:50
 * @Version
 */
public class Tair1000ImportTest {

    @Test // import Tair1000 to str table
    public void importTair1000ToStrTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_tair1000);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn, TestBase.Insert_tair1000_str, fasta.getInformation(), fasta.getSequence());
                System.out.println(filePath + " insert success!");

            }

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

    @Test // import Tair1000 to blob table
    public void importTair1000ToBlobTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_tair1000);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn, TestBase.Insert_tair1000_blob, fasta.getInformation(), fasta.getSequence().getBytes(StandardCharsets.UTF_8));
                System.out.println(filePath + " insert success!");

            }

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