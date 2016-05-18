package work.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCalculatorDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCalculatorDao.class);
    private String url = "jdbc:postgresql://localhost:5432/FirstCalculator";
    private String userSQL = "Myhome";
    private String password = "123";

    public JdbcCalculatorDao() {
        loadDriver();
    }

    public void setInfoUser(User user) throws SQLException {
        String expression = user.getExpression();
        String time = user.getTime();
        String username = user.getUsername();
        try (Connection connection = DriverManager.getConnection(url, userSQL, password);
             Statement stmt = connection.createStatement()) {
            String sql = "INSERT INTO products (expression,time, username) VALUES ('" + expression + "', '" + time + " ','" + username + "');";
            int rs = stmt.executeUpdate(sql);
            if (rs > 0)
                System.out.println("Inserted Successfully");
            else
                System.out.println("Insert Unsuccessful");
        } catch (SQLException e) {
            logger.error("Exception" + url, e);
            throw new RuntimeException(e);
        }
    }

    public User load(int id) {

        try (Connection connection = DriverManager.getConnection(url, userSQL, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM  products WHERE  username = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createEmployee(resultSet);
            } else {
                throw new RuntimeException("Can not find username");
            }
        } catch (SQLException e) {
            logger.error("Exception" + url, e);
            throw new RuntimeException(e);
        }
    }


    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        logger.info("Connection start");
        try (Connection connection = DriverManager.getConnection(url, userSQL, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM products");
            while (resultSet.next()) {
                User employee = createEmployee(resultSet);
                result.add(employee);

            }
        } catch (SQLException e) {
            logger.error("Exception" + url, e);
        }
        logger.info("Connection finish");
        return result;
    }

    private User createEmployee(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString("username"));
        user.setExpression(resultSet.getString("expression"));
        user.setTime(resultSet.getString("time"));

        return user;
    }

    private void loadDriver() {
        try {
            logger.info("logger jdbc : org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
            logger.info("logger successfully");
        } catch (ClassNotFoundException e) {
            logger.error("Can not find driver: org.postgresql.Driver  ");
            throw new RuntimeException(e);
        }
    }
}
