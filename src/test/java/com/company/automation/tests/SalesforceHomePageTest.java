package com.company.automation.tests;

import com.company.automation.pages.salesforce.SalesforceHomePage;
import com.company.automation.utils.TestDataUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public class SalesforceHomePageTest extends BaseTest {

    @Test
    public void openSalesforceHomePage() {
        Properties data = TestDataUtils.load("salesforce.properties");
        SalesforceHomePage home = new SalesforceHomePage(driver).open();

        Assert.assertTrue(
                home.getTitle().contains(TestDataUtils.get(data, "pageTitle")),
                "Salesforce home page title should contain '" + TestDataUtils.get(data, "pageTitle") + "'"
        );
    }
}
