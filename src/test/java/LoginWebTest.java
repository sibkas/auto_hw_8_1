import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.generateRandomPassword;

public class LoginWebTest {

    private final String VALID_LOGIN = "vasya";
    private final String UNENCRYPTED_PASSWORD = "password";
    private final String HASHED_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aab9099955383569421f1d1";

    @BeforeEach
    void setUp() {
        SQLHelper.cleanDB();
        SQLHelper.updateUsers(VALID_LOGIN, HASHED_PASSWORD);
        // Открытие страницы логина
        new LoginPage().openPage();
    }

    @AfterEach
    void tearDown() {
        SQLHelper.cleanDB();
    }



    @Test
    void shouldSuccessfullyLoginWithCodeFromDB() {
        // Вводим логин и пароль
        LoginPage loginPage = new LoginPage();
        loginPage.login(VALID_LOGIN, UNENCRYPTED_PASSWORD);

        // Получаем код подтверждения из БД
        String verificationCode = SQLHelper.getVerificationCode();

        // Вводим код на странице VerificationPage
        VerificationPage verificationPage = new VerificationPage();
        verificationPage.verify(verificationCode);

        // Проверяем, что DashboardPage видна
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeFailedAttempts() {
        String wrongPassword = generateRandomPassword();
        LoginPage loginPage = new LoginPage();

        for (int i = 0; i < 3; i++) {
            loginPage.login(VALID_LOGIN, wrongPassword);
        }

        loginPage.shouldShowBlockageError();
    }
}
