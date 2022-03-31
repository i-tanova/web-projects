package com.udacity.exercise.chat.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ChatPageObject {

    @FindBy(id = "messageText")
    private WebElement messageText;
    @FindBy(id = "messageType")
    private WebElement selectType;
    @FindBy(id = "username")
    private WebElement usernameTxt;
    @FindBy(id = "logout")
    private WebElement logoutBtn;

    ChatPageObject(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);

    }


    public void sendMessage(String message, String type){
        messageText.sendKeys(message);
        Select select = new Select(selectType);
        select.selectByVisibleText(type);
        messageText.submit();
    }

    public String getUsername() {
        return usernameTxt.getText();
    }

    public void logout() {
       logoutBtn.click();
    }
}
