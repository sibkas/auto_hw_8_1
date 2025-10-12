
import data.SQLHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.DataHelper.generateRandomLogin;
import static data.DataHelper.generateRandomPassword;

@Slf4j
public class AuthTest {


    @BeforeEach
    public void setUp() {
        SQLHelper.updateUsers(generateRandomLogin(), generateRandomPassword());
        SQLHelper.updateUsers(generateRandomLogin(), generateRandomPassword());
    }

    @Test
    void stubTest() {
        var count = SQLHelper.countUsers();
        log.info(String.valueOf(count));
        var first = SQLHelper.getFirstUser();
        log.info(String.valueOf(first));
        var all = SQLHelper.getUsers();
        log.info(String.valueOf(all));
    }
}
