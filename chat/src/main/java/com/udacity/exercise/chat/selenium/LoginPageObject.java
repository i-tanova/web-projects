package com.udacity.exercise.chat.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit-button")
    private WebElement submit;

    @FindBy(id = "signup-link")
    private WebElement signup;

    public LoginPageObject(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void enterUsernameAndPassword(String username, String password){
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.submit.click();
    }

    public void goToSignup(){
        this.signup.click();
    }
}
