package com.company.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots during test execution.
 */
public final class ScreenshotUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private ScreenshotUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Captures a screenshot and saves it to the screenshots directory.
     *
     * @param driver   The WebDriver instance.
     * @param testName The name of the test for naming the screenshot file.
     * @return The path to the saved screenshot file, or null if capture failed.
     */
    public static String capture(WebDriver driver, String testName) {
        if (driver == null) {
            LOG.error("WebDriver is null; cannot capture screenshot.");
            return null;
        }

        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            LOG.error("WebDriver instance does not support TakesScreenshot.");
            return null;
        }

        try {
            // 1. Capture screenshot as bytes
            byte[] screenshotBytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);

            // 2. Ensure directory exists
            Path directory = Path.of(SCREENSHOT_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // 3. Generate filename: testName-timestamp.png
            String sanitizedName = sanitize(testName);
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
            String fileName = String.format("%s-%s.png", sanitizedName, timestamp);
            Path filePath = directory.resolve(fileName);

            // 4. Write to disk
            Files.write(filePath, screenshotBytes);

            String absolutePath = filePath.toAbsolutePath().toString();
            LOG.info("Screenshot saved to: {}", absolutePath);
            return absolutePath;

        } catch (Exception e) {
            LOG.error("Failed to capture screenshot for test {}: {}", testName, e.getMessage());
            return null;
        }
    }

    private static String sanitize(String testName) {
        if (testName == null || testName.isBlank()) {
            return "screenshot";
        }
        // Replace non-alphanumeric characters with underscores
        return testName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
