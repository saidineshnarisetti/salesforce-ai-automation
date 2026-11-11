package com.company.automation.pages;

import com.company.automation.config.ConfigReader;
import com.company.automation.pages.salesforce.SalesforceHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object Model for the Salesforce Login Page.
 * Uses stable locators discovered via browser inspection.
 */
public class LoginPage extends BasePage {

    // Locators discovered via Selenium MCP
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("Login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the Salesforce login page using the configured base URL.
     * @return this page object for chaining.
     */
    public LoginPage open() {
        open(ConfigReader.getSalesforceBaseUrl());
        return this;
    }

    /**
     * Enters the provided username and triggers the transition to the password field.
     * @param username The username to enter.
     */
    public void enterUsername(String username) {
        WebElement element = wait.visible(usernameField);
        element.clear();
        element.sendKeys(username);
        element.sendKeys(org.openqa.selenium.Keys.TAB);
    }

    /**
     * Enters the provided password.
     * @param password The password to enter.
     */
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    /**
     * Clicks the Log In button.
     */
    public void clickLogin() {
        click(loginButton);
    }

    /**
     * Performs the full login flow and returns the home page.
     * This implements the multi-step Salesforce login:
     * 1. Enter username
     * 2. Click Login to reveal password field
     * 3. Enter password
     * 4. Click Login to submit
     * @param username The username to use.
     * @param password The password to use.
     * @return A new instance of SalesforceHomePage.
     */
    public SalesforceHomePage login(String username, String password) {
        enterUsername(username);
        clickLogin();
        enterPassword(password);
        clickLogin();
        return new SalesforceHomePage(driver);
    }
}
