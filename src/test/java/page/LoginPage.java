package page;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public LoginPage() {
        Selenide.open("http://localhost:9999");
    }

    public void login(String login, String password) {
        $("[data-test-id='login'] .input__control").shouldBe(visible).setValue(login);
        $("[data-test-id='password'] .input__control").shouldBe(visible).setValue(password);
        $("[data-test-id='action-login']").shouldBe(visible).click();
    }

    public void shouldShowBlockageError() {
        $("[data-test-id='error-notification']")
                .shouldBe(visible)
                .shouldHave(text("ошибка"));
    }
}
