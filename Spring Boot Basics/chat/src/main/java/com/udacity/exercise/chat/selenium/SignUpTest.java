package com.udacity.exercise.chat.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPageObject loginPageObject;
    private ChatPageObject chatPageObject;

    @BeforeAll
    public static void beforeAll(){
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
        chatPageObject = new ChatPageObject(driver);
    }

    @Test
    public void testSignUp(){
        signupPage.signUp("Ivana", "Tanova", "fallenstar", "123456");
        driver.get("http://localhost:" + port + "/login");
        loginPageObject.enterUsernameAndPassword("fallenstar", "123456");
        driver.get("http://localhost:" + port + "/chat");
        chatPageObject.sendMessage("Message", "Shout");
        String username = chatPageObject.getUsername();
        System.out.println(username);
        assertEquals("fallenstar", username);
    }


}
