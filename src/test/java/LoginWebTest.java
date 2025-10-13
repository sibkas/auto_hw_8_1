import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;
import static data.DataHelper.generateRandomPassword;

public class LoginWebTest {

    private final String VALID_LOGIN = "vasya";
    private final String UNENCRYPTED_PASSWORD = "qwerty123";
    private final String HASHED_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aab9099955383569421f1d1";

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        SQLHelper.cleanDB();
        SQLHelper.updateUsers(VALID_LOGIN, HASHED_PASSWORD);
        // Открытие страницы логина
        loginPage = new LoginPage();
    }

    @AfterAll
    static void tearDownAll() {
        SQLHelper.cleanDB();
    }



    @Test
    void shouldSuccessfullyLoginWithCodeFromDB() {
        // Вводим логин и пароль

        loginPage.login(VALID_LOGIN, UNENCRYPTED_PASSWORD);

        // Получаем код подтверждения из БД
        String verificationCode = SQLHelper.getVerificationCode();

        // Вводим код на странице VerificationPage
        VerificationPage verificationPage = new VerificationPage();
        verificationPage.verify(verificationCode);

        // Проверяем, что DashboardPage видна
        DashboardPage dashboardPage = new DashboardPage();

    }

    @Test
    void shouldBlockUserAfterThreeFailedAttempts() {
        String wrongPassword = generateRandomPassword();


        for (int i = 0; i < 3; i++) {
            loginPage.login(VALID_LOGIN, wrongPassword);
        }

        loginPage.shouldShowBlockageError();
    }

    @Test
    void shouldVerifyWithCode() {
        VerificationPage verificationPage = new VerificationPage();
        verificationPage.verify("12345");
    }
}
