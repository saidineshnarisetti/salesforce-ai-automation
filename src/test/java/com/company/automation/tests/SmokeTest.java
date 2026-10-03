package com.automation.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest {

    @Test
    public void openGoogle() {

        WebDriver driver = new ChromeDriver();

        try {

            driver.get("https://www.google.com");

            String title = driver.getTitle();

            System.out.println(
                    "Page title: " + title
            );

            Assert.assertTrue(
                    title.contains("Google")
            );

        } finally {

            driver.quit();
        }
    }
}