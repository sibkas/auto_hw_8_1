package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.util.UUID;
import java.sql.*;
import java.util.List;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "1234");
    }

    @SneakyThrows
    public static void updateUsers(String login, String password) {
        String id = UUID.randomUUID().toString(); // Генерация UUID
        String sql = "INSERT INTO users (id, login, password) VALUES (?, ?, ?)";
        try (var conn = getConnection()) {
            runner.update(conn, sql, id, login, password);
        }
    }

    @SneakyThrows
    public static long countUsers() {
        var countSQL = "SELECT COUNT(*) FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, countSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static User getFistUser(){
        var usersSQL = "SELECT * FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, usersSQL, new BeanHandler<>(User.class));
        }
    }

    @SneakyThrows
    public static List<User> getUser(){
        var usersSQL = "SELECT * FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
        }
    }
}
