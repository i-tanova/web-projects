package com.udacity.jwdnd.course1.cloudstorage.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest {
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
    public void testHomePageNotAccessible() {
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        try {
            homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        } catch (TimeoutException e) {
            // Expected
        }
        assertNull(homeMarker);

        WebDriverWait wait2 = new WebDriverWait(driver, 3);
        WebElement marker2 = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(marker2);
    }

    @Test
    public void testLoginPageAccessible() {
        driver.get("http://localhost:" + port + "/login");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(homeMarker);
    }

    @Test
    public void testSignupPageAccessible() {
        driver.get("http://localhost:" + port + "/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(homeMarker);
    }

    @Test
    public void testHomePageIsAccessibleAfterSignup() {
        //Signup
        signupPage.signUp("Ivana", "Tanova", "fallenstar", "123456");

        //Login
        driver.get("http://localhost:" + port + "/login");
        loginPageObject.enterUsernameAndPassword("fallenstar", "123456");

        // Go to home
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);

        // Logout
        homePageObject.logout();

        // Test home page not accessible
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait2 = new WebDriverWait(driver, 3);
        WebElement homeMarker2 = null;
        try {
            homeMarker2 = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        } catch (TimeoutException e) {
            // Expected
        }
        assertNull(homeMarker2);
    }


}
