package com.udacity.jwdnd.course1.cloudstorage.selenium;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPageObject loginPageObject;
    private HomePageObject homePageObject;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(driver);
        loginPageObject = new LoginPageObject(driver);
        homePageObject = new HomePageObject(driver);
    }

    @Test
    public void testCreateCredentials() throws InterruptedException {
        //Signup
        signUpAndGoToHome("CreateCredentials");

        List<HomePageObject.CredentialInTest> credentialsToSubmit = new ArrayList<>();
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me.com", "me", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me1.com", "me1", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me2.com", "me2", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me3.com", "me3", "1234567"));

        homePageObject.enterCredentials(credentialsToSubmit);

        List<HomePageObject.CredentialInTest> displayedCredentials = homePageObject.getDisplayedCredentials();

        for (int i = 0; i < credentialsToSubmit.size(); i++) {
            String id = displayedCredentials.get(i).getId();
            System.out.println("Displayed credential id " + id);
            HomePageObject.CredentialInTest submittedCredential = credentialsToSubmit.get(i);
            HomePageObject.CredentialInTest displayedCredential = displayedCredentials.get(i);
            assertEquals(submittedCredential.getUrl(), displayedCredential.getUrl());
            assertEquals(submittedCredential.getUsername(), displayedCredential.getUsername());

            /// Assert password is not displayed
            assertNotEquals(submittedCredential.getPassword(), displayedCredential.getPassword());
        }
    }

    @Test
    public void testCreateCredentialsEdit() throws InterruptedException {
        //Signup
        signUpAndGoToHome("EditCredential");

        List<HomePageObject.CredentialInTest> credentialsToSubmit = new ArrayList<>();
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me.com", "me", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me1.com", "me1", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me2.com", "me2", "1234567"));

        homePageObject.enterCredentials(credentialsToSubmit);

        // TEST Password is shown unencripted
        List<HomePageObject.CredentialInTest> displayedCredentials = homePageObject.getDisplayedCredentials();
        List<HomePageObject.CredentialInTest> credentialsEdit = new ArrayList<>();
        // Check password is unencrypted
        for (int i = 0; i < displayedCredentials.size(); i++) {
            String showedPassword = homePageObject.getShowedPasswordForCredentialId(displayedCredentials.get(i).getId());
            assertEquals(credentialsToSubmit.get(i).getPassword(), showedPassword);
        }

        // TEST edit
        for (int i = 0; i < displayedCredentials.size(); i++) {
            HomePageObject.CredentialInTest displayedCredential = displayedCredentials.get(i);
            credentialsEdit.add(new HomePageObject.CredentialInTest(displayedCredential.getId(), "New url " +i, "new username " + i, "new password " + i));
        }

        homePageObject.editCredentials(credentialsEdit);

        List<HomePageObject.CredentialInTest> displayedCredentialsAfterEdit = homePageObject.getDisplayedCredentials();
        for (int i = 0; i < displayedCredentialsAfterEdit.size(); i++) {
            HomePageObject.CredentialInTest displayedCredential = displayedCredentialsAfterEdit.get(i);
            String showedPassword = homePageObject.getShowedPasswordForCredentialId(displayedCredential.getId());
            assertEquals("new password " + i, showedPassword);
            assertEquals("new username " + i, displayedCredential.getUsername());
            assertEquals("New url " + i, displayedCredential.getUrl());
        }
    }

    @Test
    public void testDeleteCredential() throws InterruptedException {
        //Signup
        signUpAndGoToHome("DeleteCredential");

        List<HomePageObject.CredentialInTest> credentialsToSubmit = new ArrayList<>();
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me.com", "me", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me1.com", "me1", "1234567"));
        credentialsToSubmit.add(new HomePageObject.CredentialInTest("", "http://me2.com", "me2", "1234567"));

        homePageObject.enterCredentials(credentialsToSubmit);

        // TEST Credentials are created
        List<HomePageObject.CredentialInTest> displayedCredentials = homePageObject.getDisplayedCredentials();
        assertEquals(credentialsToSubmit.size(), displayedCredentials.size());

        homePageObject.deleteCredentials(displayedCredentials);

        List<HomePageObject.CredentialInTest> displayedCredentialsAfterDelete = homePageObject.getDisplayedCredentials();
        assertEquals(0, displayedCredentialsAfterDelete.size());
    }

    private void signUpAndGoToHome(String username) {
        signupPage.signUp("Test", "test", username, "123456");

        //Login
        driver.get("http://localhost:" + port + "/login");
        loginPageObject.enterUsernameAndPassword(username, "123456");

        // Go to home
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);
    }
}
