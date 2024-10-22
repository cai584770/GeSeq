package exp.postgre;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author cai584770
 * @date 2024/10/21 16:39
 * @Version
 */
public class ConnectionTest {

    @Test
    public void connect(){
        try (Connection connection = DriverManager.getConnection(PostgreSQLBase.url, PostgreSQLBase.user, PostgreSQLBase.password)) {
            if (connection != null) {
                System.out.println("Successfully connected to PostgreSQL database!");
            } else {
                System.out.println("Failed to connect to PostgreSQL database.");
            }
        } catch (SQLException e) {
            System.out.println("PostgreSQL connection failed.");
            e.printStackTrace();
        }
    }




}
