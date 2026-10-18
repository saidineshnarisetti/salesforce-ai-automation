package com.company.automation.driver;

import com.company.automation.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Thread-local WebDriver lifecycle. Each TestNG thread owns its own browser session.
 */
public final class WebDriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private WebDriverFactory() {
    }

    public static void initializeDriver() {
        initializeDriver(ConfigReader.get("browser"));
    }

    public static void initializeDriver(String browser) {
        if (DRIVER.get() != null) {
            throw new IllegalStateException("WebDriver already initialized for this thread");
        }
        String resolvedBrowser = browser == null || browser.isBlank()
                ? ConfigReader.get("browser")
                : browser.trim();
        boolean headless = ConfigReader.getBoolean("headless");
        WebDriver driver = createDriver(resolvedBrowser, headless);
        driver.manage().timeouts().pageLoadTimeout(ConfigReader.getDurationSeconds("pageLoadTimeoutSeconds"));
        driver.manage().timeouts().scriptTimeout(ConfigReader.getDurationSeconds("scriptTimeoutSeconds"));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        DRIVER.set(driver);
        LOG.info("Started {} WebDriver (headless={}) on thread {}",
                resolvedBrowser, headless, Thread.currentThread().getName());
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been initialized for this thread");
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
            LOG.info("Quit WebDriver on thread {}", Thread.currentThread().getName());
        } finally {
            DRIVER.remove();
        }
    }

    private static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            case "edge" -> new EdgeDriver(edgeOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        return options;
    }
}
