import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;
import static data.DataHelper.generateRandomPassword;

public class LoginWebTest {

    private final String VALID_LOGIN = DataHelper.getValidLogin();
    private final String UNENCRYPTED_PASSWORD = DataHelper.getUnencryptedPassword();
    private final String HASHED_PASSWORD = DataHelper.getHashedPassword();

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {

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
        // Получаем код подтверждения из БД
        String verificationCode = SQLHelper.getVerificationCode();
        VerificationPage verificationPage = new VerificationPage();
        verificationPage.verify(verificationCode);
    }
}
