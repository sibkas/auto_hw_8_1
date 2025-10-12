package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class SQLHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @SneakyThrows
    public static void updateUsers(String login, String password) {
        String id = UUID.randomUUID().toString();
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
    public static User getFirstUser() {
        var usersSQL = "SELECT * FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, usersSQL, new BeanHandler<>(User.class));
        }
    }

    @SneakyThrows
    public static List<User> getUsers() {
        var usersSQL = "SELECT * FROM users;";
        try (var conn = getConnection()) {
            return runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
        }
    }

    @SneakyThrows
    public static void cleanDB() {
        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM auth_codes;");
            runner.update(conn, "DELETE FROM cards;");
            runner.update(conn, "DELETE FROM users;");
        }
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getVerificationCodeFor(String login) {
        var sql = "SELECT code FROM auth_codes WHERE login = ? ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        }
    }


}
