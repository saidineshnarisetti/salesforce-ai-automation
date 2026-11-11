package com.company.automation.tests;

import com.company.automation.pages.google.GoogleHomePage;
import com.company.automation.utils.TestDataUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public class SmokeTest extends BaseTest {

    @Test
    public void openGoogle() {
        Properties data = TestDataUtils.load("google.properties");
        GoogleHomePage google = new GoogleHomePage(driver).open();

        Assert.assertTrue(
                google.getTitle().contains(TestDataUtils.get(data, "pageTitle")),
                "Google home page title should contain '" + TestDataUtils.get(data, "pageTitle") + "'"
        );
        Assert.assertTrue(google.isSearchFieldVisible(), "Search field should be visible");
    }
}
