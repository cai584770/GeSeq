package exp.postgre.base;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author cai584770
 * @date 2024/10/22 9:02
 * @Version
 */
public class CreateTypeAndTableTest {

    @Test
    public void createGeSeqType() {
        try (Connection connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)) {
            String sql = "CREATE TYPE GeSeq AS (sequence bytea, lowercase bytea, nbase bytea, otherbase bytea, sequencelength bigint, nucleotideslength bigint);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createGeSeqTable() {
        try (Connection connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)) {
            String sql = "CREATE TABLE gene (id serial PRIMARY KEY, information text, my_geseq geseq);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createStringTable() {
        try (Connection connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)) {
            String sql = "CREATE TABLE gene_str (id serial PRIMARY KEY, information text, sequence text);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}