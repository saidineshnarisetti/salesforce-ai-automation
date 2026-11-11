package com.company.automation.tests;

import com.company.automation.config.ConfigReader;
import com.company.automation.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void loadConfiguration() {
        ConfigReader.load();
        LOG.info("Loaded automation configuration");
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {
        WebDriverFactory.initializeDriver(browser);
        driver = WebDriverFactory.getDriver();
        if (!ConfigReader.getBoolean("headless")) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverFactory.quitDriver();
        driver = null;
    }
}
