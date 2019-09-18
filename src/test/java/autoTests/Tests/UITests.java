package autoTests.Tests;

import autoTests.Config;
import autoTests.JsonDataFileLocation;
import autoTests.PageObjects.*;
import autoTests.TestBase;
import autoTests.User;
import com.google.gson.annotations.SerializedName;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

@Epic("UI Tests")
public class UITests extends TestBase {

    class CustomTestData {
        @SerializedName("user")
        List<User> users;
        String subject;
        String body;
        String successBody;
        String expectedFileContent;
        String searchString;
        int createMessageCount;
        int unreadMessageIndex;
        int successMessageIndex;
        List<String> emails;
    }

    @JsonDataFileLocation("data/test1.json")
    @Test(dataProvider = "customDataProvider", description = "1. Работа с почтой")
    public void TestMethod(CustomTestData testData) {
        open(Config.getInstance().getBaseUrl());
        //Step 1
        new LoginPage().login(testData.users.get(0));
        //Step 2
        for (int i = 0; i < testData.createMessageCount; i++) {
            MessageComposePage msg = new MailBoxPage().openFolder("Входящие").clickNewMessageBtn();
            msg
                    .attachLocalFile(Config.getInstance().getTestTextFile())
                    .setEmailTo(testData.emails.get(1))
                    .setSubject(testData.subject)
                    .setBody(testData.body)
                    .clickSendBtn();
        }
        //Step 3
        new TopMenu().logout();
        new LoginPage().login(testData.users.get(1));
        MessagePage m = new MailBoxPage().openUnreadMessage(testData.searchString, testData.unreadMessageIndex);
        Assert.assertEquals(m.getSubject(), testData.subject);
        Assert.assertTrue(m.getBody().contains(testData.body));
        Assert.assertEquals(m.getTextAttachmentContent(), testData.expectedFileContent);
        //Step 4
        MessageComposePage msg = m.clickReplyBtn();
        msg.setBody(testData.successBody).clickSendBtn();
        new TopMenu().logout();
        new LoginPage().login(testData.users.get(0));
        new MailBoxPage().openFolder("Входящие").openUnreadMessage(testData.successBody, testData.successMessageIndex);
        Assert.assertTrue(m.getBody().contains(testData.successBody));
    }
}
