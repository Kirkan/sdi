package autoTests.PageObjects;

import autoTests.Config;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class TopMenu {

    private SelenideElement mainContainer = $x(".//div[@id='headline']");

    public TopMenu() {
        mainContainer.shouldBe(Condition.visible.because("Меню должно отображаться"));
    }

    //region Методы
    @Step("Logout")
    public TopMenu logout() {
        mainContainer.$x(".//a[@id='PH_logoutLink']").shouldBe(Condition.visible).click();
        open(Config.getInstance().getBaseUrl());
        return this;
    }

    //endregion

}
