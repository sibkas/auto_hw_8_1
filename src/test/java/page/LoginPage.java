package page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public LoginPage() {
        Selenide.open("http://localhost:9999");
    }

    public void login(String login, String password) {
        SelenideElement loginInput = $("[data-test-id='login'] .input__control").shouldBe(visible);
        SelenideElement passwordInput = $("[data-test-id='password'] .input__control").shouldBe(visible);

        // Очистка полей: выделение всего текста и удаление
        loginInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        passwordInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);

        // Ввод новых значений
        loginInput.setValue(login);
        passwordInput.setValue(password);

        $("[data-test-id='action-login']").shouldBe(visible).click();
    }


    public void shouldShowLoginError() {
        $("[data-test-id='error-notification']")
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
