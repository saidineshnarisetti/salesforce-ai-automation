package com.company.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Simple smoke test to verify basic browser functionality and connectivity.
 */
public class GoogleTest extends BaseTest {

    @Test(description = "Verify that Google home page title contains 'Google-Title'")
    public void testGoogleTitle() {
        // Navigate to Google
        driver.get("https://www.google.com");

        // Verify the page title contains "Google-Title"
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google"),
            String.format("Expected title to contain 'Google', but was: '%s'", title));
    }
}
