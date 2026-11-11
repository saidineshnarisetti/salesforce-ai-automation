package com.company.automation.pages.google;

import com.company.automation.config.ConfigReader;
import com.company.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage extends BasePage {

    private static final By SEARCH_FIELD = By.name("q");

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleHomePage open() {
        open(ConfigReader.get("baseUrl"));
        wait.visible(SEARCH_FIELD);
        return this;
    }

    public boolean isSearchFieldVisible() {
        return isDisplayed(SEARCH_FIELD);
    }

    public GoogleHomePage search(String term) {
        typeAndSubmit(SEARCH_FIELD, term);
        return this;
    }
}
