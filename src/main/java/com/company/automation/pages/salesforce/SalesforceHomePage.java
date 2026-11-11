package com.company.automation.pages.salesforce;

import com.company.automation.config.ConfigReader;
import com.company.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesforceHomePage extends BasePage {

    private static final By SKIP_TO_CONTENT = By.linkText("Skip to content");

    public SalesforceHomePage(WebDriver driver) {
        super(driver);
    }

    public SalesforceHomePage open() {
        open(ConfigReader.get("salesforceUrl"));
        wait.titleContains("Salesforce");
        return this;
    }

    public boolean hasSkipToContentLink() {
        return wait.present(SKIP_TO_CONTENT).isDisplayed();
    }
}
