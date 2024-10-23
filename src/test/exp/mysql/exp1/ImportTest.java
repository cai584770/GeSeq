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
 * @date 2024/10/15 15:15
 * @Version
 */
public class ImportTest {

    @Test // import ce11 to str table
    public void importCE11ToStrTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_CE11);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_ce11_str,fasta.getInformation(),fasta.getSequence());
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

    @Test // import ce11 to blob table
    public void importCE11ToBlobTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_CE11);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_ce11_blob,fasta.getInformation(), fasta.getSequence().getBytes(StandardCharsets.UTF_8));
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

    @Test // import sacCer3 to str table
    public void importSacCer3ToStrTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_sacCer3);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_sacCer3_str,fasta.getInformation(),fasta.getSequence());
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

    @Test // import sacCer3 to blob table
    public void importSacCer3ToBlobTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_sacCer3);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_sacCer3_blob,fasta.getInformation(), fasta.getSequence().getBytes(StandardCharsets.UTF_8));
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

    @Test // import hg38 to str table
    public void importHg38ToStrTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_HG38);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_hg38_str,fasta.getInformation(),fasta.getSequence());
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

    @Test // import hg38 to blob table
    public void importHg38ToBlobTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(TestBase.JDBC_URL, TestBase.USER, TestBase.PASS);
            stmt = conn.createStatement();
            String useDb = "USE geseq";
            stmt.executeUpdate(useDb);

            List<String> allFilePaths = FilePath.getAllFilePaths(TestBase.Path_HG38);

            for (String filePath : allFilePaths) {
                FASTA fasta = FileProcessor.getFASTAFromFile(filePath);
                Insert.insertData(conn,TestBase.Insert_hg38_blob,fasta.getInformation(), fasta.getSequence().getBytes(StandardCharsets.UTF_8));
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
