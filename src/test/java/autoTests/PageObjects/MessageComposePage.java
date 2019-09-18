package autoTests.PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;

import java.io.File;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.AssertJUnit.fail;

public class MessageComposePage {

    private SelenideElement messageContainer = $x(".//div[contains(@class, 'compose-app') and contains(@class, 'compose-app_window')][@message-uid]");
    private SelenideElement localUpload = $x(".//div[contains(@class, 'attach_container')]//div[contains(@class, 'controls_container')]//button/input[contains(@class,'desktopInput')]");

    public MessageComposePage() {
        messageContainer.shouldBe(Condition.visible.because("Страница должна отображаться"));
    }

    //region Методы
    @Step("Заполняем поле 'Кому' {0}")
    public MessageComposePage setEmailTo(String value) {
        SelenideElement input = $x(".//div[contains(@class, 'head_container')]//div[contains(@class, 'inputContainer')]/input").shouldBe(Condition.visible);
        input.clear();
        input.sendKeys(value);
        return this;
    }

    @Step("Заполняем поле 'Тема сообщения'{0}")
    public MessageComposePage setSubject(String value) {
        SelenideElement input = $x(".//div[contains(@class, 'subject__container')]//div[contains(@class, 'inputContainer')]/input").shouldBe(Condition.visible);
        input.clear();
        input.sendKeys(value);
        return this;
    }

    @Step("Заполняем поле 'Текст сообщения'{0}")
    public MessageComposePage setBody(String value) {
        $x(".//div[contains(@class, 'editor_container')]//div[contains(@class, 'editable-container')]/div").shouldBe(Condition.visible).sendKeys(value);
        return this;
    }

    @Step("Нажимаем кнопку 'Отправить'")
    public void clickSendBtn() {
        $x(".//div[@class='compose-app__footer']/div[@class='compose-app__buttons']/span[@title='Отправить']").shouldBe(Condition.visible).click();
        $x(".//div[@class='layer__header' and normalize-space(contains(.,'Письмо отправлено'))]").shouldBe(Condition.visible);
        $x(".//span[@title='Закрыть']").shouldBe(Condition.visible).click();
    }

    @Step("Добавляем вложение к письму")
    public MessageComposePage attachLocalFile(File file) {
        try {
            ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", localUpload, "style", "display: block;");
        } catch (WebDriverException we) {
            fail("Script can't be executed");
        }
        localUpload.sendKeys(file.getAbsolutePath());
        return this;
    }


    //endregion

}
