package page;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    public VerificationPage() {
        $("[data-test-id=code] input").shouldBe(Condition.visible);
    }

    public void verify(String code) {
        $("[data-test-id=code] input").shouldBe(Condition.visible).setValue(code);
        $("[data-test-id=action-verify]").shouldBe(Condition.visible).click();
    }
}
