package exp.mysql.connect;

import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author cai584770
 * @date 2024/10/15 15:11
 * @Version
 */
public class ConnectTest {

    @Test
    public void connectTest() {
        try {
            // 1、反射注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2、获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentstatus", "root", "cai584770");
            System.out.println(connection);

            // 3、获取数据库操作对象
            Statement statement = connection.createStatement();

            // 4、执行SQL语句
            ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher");

            // 5、处理查询结果
            while (resultSet.next()) {
                int id = resultSet.getInt("teacher_no");
                String name = resultSet.getString("teacher_name");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // 6、释放资源
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
