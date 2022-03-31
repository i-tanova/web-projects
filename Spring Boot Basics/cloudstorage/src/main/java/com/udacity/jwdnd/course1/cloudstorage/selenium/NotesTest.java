package com.udacity.jwdnd.course1.cloudstorage.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest {
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
    public void testNoteCreated() throws InterruptedException {
        createNote("createNote", "12345");
    }

    private void createNote(String name, String password) throws InterruptedException {
        signupAndLogin(name, password);

        // Go to home
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);

        // Enter note
        homePageObject.enterNote("Title", "Text");
        Thread.sleep(3000);

        // Check note added
        assertEquals("Title", homePageObject.getFirstNoteTitle());
        assertEquals("Text", homePageObject.getFirstNoteText());
    }

    private void signupAndLogin(String name, String password) {
        //Signup
        signupPage.signUp("Ivana", "Tanova", name, password);

        //Login
        driver.get("http://localhost:" + port + "/login");
        loginPageObject.enterUsernameAndPassword(name, password);
    }

    @Test
    public void testNoteEdited() throws InterruptedException {
        createNote("editNote", "12345");
        homePageObject.editNote("New Title", "New Text");
        Thread.sleep(3000);

        // Check
        assertEquals("New Title", homePageObject.getFirstNoteTitle());
        assertEquals("New Text", homePageObject.getFirstNoteText());
    }

    @Test
    public void testNoteDeleted() throws InterruptedException {
        createNote("deleteNote", "12345");
        homePageObject.deleteNote();
        Thread.sleep(3000);

        // Check
        try{
            homePageObject.getFirstNoteTitle();
            fail("Note not deleted");
        }catch (NoSuchElementException e){
            assertTrue(true);
        }
    }
}
