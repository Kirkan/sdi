package autoTests.PageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.HashSet;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;

public class MessagePage {

    private SelenideElement messageContainer = $x(".//div[contains(@style, 'display: table;')]");

    public MessagePage() {
        messageContainer.shouldBe(Condition.visible.because("Страница должна отображаться"));
    }

    //region Методы
    @Step("Получаем текст сообщения")
    public String getBody() {
        return messageContainer.$x(".//div[contains(@class,'letter-body')]//div[@data-signature-widget]/parent::div").shouldBe(Condition.visible).getText();
    }

    @Step("Получаем тему сообщения")
    public String getSubject() {
        return messageContainer.$x(".//h2[@class='thread__subject']").shouldBe(Condition.visible).getText();
    }

    @Step("Нажать кнопку 'Ответить'")
    public MessageComposePage clickReplyBtn() {
        messageContainer.$x(".//div[@class='letter__footer']//span[@title='Ответить' or .='Ответить']").shouldBe(Condition.visible).click();
        return new MessageComposePage();
    }

    @Step("Получаем текст вложения")
    public String getTextAttachmentContent() {
        String content, attachWindow = null;
        messageContainer.$x(".//div[@class='attach-list']//div[contains(@class, 'attach-thumb')]").shouldBe(Condition.visible).click();
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() > 1) {
            attachWindow = nextWindowHandle(WebDriverRunner.getWebDriver());
            switchTo().window(attachWindow);
        }
        switchTo().frame($("iframe"));
        content = $("pre").getText();
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() > 1) {
            switchTo().window(attachWindow).close();
            switchTo().window(0);
        }
        return content;
    }

    private String nextWindowHandle(WebDriver driver) {
        String windowHandle = driver.getWindowHandle();
        Set<String> windowHandles = new HashSet<>(driver.getWindowHandles());
        windowHandles.remove(windowHandle);
        return windowHandles.iterator().next();
    }

    //endregion

}
