package com.company.automation.tests;

import com.company.automation.config.ConfigReader;
import com.company.automation.pages.LoginPage;
import com.company.automation.pages.salesforce.SalesforceHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test suite for Salesforce Login functionality.
 */
public class SalesforceLoginTest extends BaseTest {

    @Test(description = "Verify that a user can successfully log in to Salesforce with valid credentials")
    public void successfulSalesforceLogin() {
        // 1. Initialize Page Objects
        LoginPage loginPage = new LoginPage(driver);

        // 2. Open Salesforce login URL
        loginPage.open();

        // 3 & 4. Read credentials from environment variables via ConfigReader
        String username = ConfigReader.getSalesforceUsername();
        String password = ConfigReader.getSalesforcePassword();

        // 5. Use LoginPage.login()
        SalesforceHomePage homePage = loginPage.login(username, password);

        // 6 & 7. Verify navigation and successful login
        // The login method returns SalesforceHomePage, which is the target state.
        // We verify a specific element on the home page to confirm login success.
        Assert.assertTrue(homePage.hasSkipToContentLink(),
            "User should be redirected to the Salesforce Home Page after successful login.");

        // 8. Additional meaningful assertion: Verify page title contains 'Salesforce'
        Assert.assertTrue(driver.getTitle().contains("Salesforce"),
            "Page title should contain 'Salesforce' after login.");
    }
}
