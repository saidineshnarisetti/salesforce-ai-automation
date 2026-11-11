package com.company.automation.listeners;

import com.company.automation.driver.WebDriverFactory;
import com.company.automation.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener to handle test lifecycle events, logging, and failure screenshots.
 */
public class TestListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("Starting test: {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("Test Passed: {}", result.getMethod().getQualifiedName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LOG.error("Test Failed: {}", result.getMethod().getQualifiedName());

        // Log exception
        if (result.getThrowable() != null) {
            LOG.error("Exception: ", result.getThrowable());
        }

        // Capture screenshot and log current URL
        if (WebDriverFactory.hasDriver()) {
            WebDriver driver = WebDriverFactory.getDriver();

            try {
                String currentUrl = driver.getCurrentUrl();
                LOG.error("Current URL at failure: {}", currentUrl);
            } catch (Exception e) {
                LOG.warn("Could not retrieve current URL: {}", e.getMessage());
            }

            String screenshotPath = ScreenshotUtils.capture(driver, testName);
            if (screenshotPath != null) {
                LOG.info("Failure screenshot saved to: {}", screenshotPath);
                result.setAttribute("screenshot", screenshotPath);
            } else {
                LOG.error("Failed to capture screenshot for test: {}", testName);
            }
        } else {
            LOG.warn("No WebDriver available to capture screenshot for test: {}", testName);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("Test Skipped: {}", result.getMethod().getQualifiedName());
    }
}
