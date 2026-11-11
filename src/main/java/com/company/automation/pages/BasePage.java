package com.company.automation.pages;

import com.company.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shared page actions. Locators stay in concrete page classes.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected WebElement findVisible(By locator) {
        return wait.visible(locator);
    }

    protected void click(By locator) {
        wait.clickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void typeAndSubmit(By locator, String text) {
        WebElement element = wait.visible(locator);
        element.clear();
        element.sendKeys(text, Keys.ENTER);
    }

    protected String textOf(By locator) {
        return wait.visible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return wait.visible(locator).isDisplayed();
    }

    protected void open(String url) {
        driver.get(url);
    }
}
