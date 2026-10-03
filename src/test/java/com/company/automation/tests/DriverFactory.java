package com.company.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    public static void initializeDriver() {

        DRIVER.set(new ChromeDriver());

    }

    public static WebDriver getDriver() {

        return DRIVER.get();

    }

    public static void quitDriver() {

        if (DRIVER.get() != null) {

            DRIVER.get().quit();

            DRIVER.remove();
        }
    }
}