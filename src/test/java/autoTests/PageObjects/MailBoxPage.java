package autoTests.PageObjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MailBoxPage {

    private ElementsCollection allLetters = $$x(".//a[contains(@class, 'list-letter-spinner')]/parent::div/span//span/a/a[@data-id]");
    private ElementsCollection unreadLetters = $$x(".//a[contains(@class, 'list-letter-spinner')]/parent::div//a[contains(@class, 'js-letter-list-item')][./div/span[@title='Пометить прочитанным' or @data-title='Пометить прочитанным']]");
    private ElementsCollection folders = $$x(".//nav[@class]//div");
    private SelenideElement composeBtn = $x(".//span[@class='compose-button__txt']");

    public MailBoxPage() {
        $x(".//div[@class='application-mail']").shouldBe(Condition.visible.because("Страница должна отображаться"));
    }

    //region Методы
    @Step("Открываем папку {0}")
    public MailBoxPage openFolder(String folderName) {
        folders.shouldHave(CollectionCondition.sizeGreaterThan(1)).find(Condition.text(folderName)).click();
        return this;
    }

    @Step("Нажимаем кнопку Написать сообщение")
    public MessageComposePage clickNewMessageBtn() {
        composeBtn.shouldBe(Condition.visible).click();
        return new MessageComposePage();
    }

    @Step("Открываем непрочитанное сообщение {0} под индексом {1}")
    public MessagePage openUnreadMessage(String searchString, int index) {
        unreadLetters.filter(Condition.text(searchString)).get(index).click();
        return new MessagePage();
    }

    //endregion

}
