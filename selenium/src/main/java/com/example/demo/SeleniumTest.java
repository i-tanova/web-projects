package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/animal");


        WebElement inputField = driver.findElement(By.id("animalText"));
        inputField.sendKeys("Hi");

        WebElement inputField2 = driver.findElement(By.id("adjective"));
        inputField2.sendKeys("Hi");
        for (int i = 0; i < 5; i++) {
            //take the element every time because dom is restarted
            inputField2 = driver.findElement(By.id("adjective"));
            inputField2.submit();
            //WebElement subitBtn = driver.findElement(By.xpath("//form[1]/input[3]"));
            //subitBtn.click();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        WebElement conclusion = driver.findElement(By.className("conclusionMessage"));
        System.out.println(conclusion.getText());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
