package com.company.automation.pages.saucedemo;

import com.company.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SauceDemoLoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");

    public SauceDemoLoginPage(WebDriver driver) {
        super(driver);
    }

    public SauceDemoInventoryPage login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new SauceDemoInventoryPage(driver);
    }

    public SauceDemoLoginPage openPage(String url) {
        open(url);
        return this;
    }
}
