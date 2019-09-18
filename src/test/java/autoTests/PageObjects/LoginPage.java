package autoTests.PageObjects;

import autoTests.User;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    public LoginPage() {
        $x(".//div[@id='mailbox-container']").shouldBe(Condition.visible.because("Страница должна отображаться"));
    }

    //region Методы
    @Step("Авторизация пользователя {userData.login}:{userData.password}")
    public void login(User userData) {
        setLogin(userData.getLogin());
        clickActionBtn();
        setPassword(userData.getPassword());
        clickActionBtn();
    }

    @Step("Вводим логин {0}")
    public LoginPage setLogin(String value) {
        $x(".//input[@id='mailbox:login']").shouldBe(Condition.visible).setValue(value);
        return this;
    }

    @Step("Нажимаем кнопку подтверждения/ввода")
    public LoginPage clickActionBtn() {
        $x(".//label[@id='mailbox:submit']").shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Вводим пароль {0}")
    public LoginPage setPassword(String value) {
        $x(".//input[@id='mailbox:password']").shouldBe(Condition.visible).setValue(value);
        return this;
    }
    //endregion

}
