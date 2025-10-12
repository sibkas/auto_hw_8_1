import com.github.javafaker.Faker;
import data.User;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.util.List;
import java.util.UUID;

public class DBUtils {

    @SneakyThrows
    @BeforeEach
    void setUp() {
        var faker = new Faker();
        var runner = new QueryRunner();
        var dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";

        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {
            runner.update(conn, dataSQL, UUID.randomUUID().toString(), faker.name().username(), "pass");
            runner.update(conn, dataSQL, UUID.randomUUID().toString(), faker.name().username(), "pass");
        }
    }

    @Test
    @SneakyThrows
    void stubTest() {
        var countSQL = "SELECT COUNT(*) FROM users;";
        var usersSQL = "SELECT * FROM users;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "root", "1234")) {
            var count = runner.query(conn, countSQL, new ScalarHandler<>());
            System.out.println(count);
            User first = runner.query(conn, usersSQL, new BeanHandler<>(User.class));
            System.out.println(first);
            List<User> all = runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
            System.out.println(all);
        }
    }
}
