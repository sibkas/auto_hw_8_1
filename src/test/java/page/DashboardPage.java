package page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public DashboardPage() {
        $("h2").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }
}
