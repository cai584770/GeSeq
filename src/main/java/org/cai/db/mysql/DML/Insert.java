package org.cai.db.mysql.DML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author cai584770
 * @date 2024/10/15 19:34
 * @Version
 */
public class Insert {
    public static void insertData(Connection conn, String sql, String information, Object sequence) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, information);
            if (sequence instanceof String) {
                pstmt.setString(2, (String) sequence);
            } else if (sequence instanceof byte[]) {
                pstmt.setBytes(2, (byte[]) sequence);
            } else {
                throw new IllegalArgumentException("Unsupported sequence type.");
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected <= 0) {
                System.out.println("Insert error!");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
