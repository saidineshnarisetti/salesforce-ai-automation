package com.company.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Loads key/value test data from {@code testdata/*.properties} on the classpath.
 */
public final class TestDataUtils {

    private static final String FOLDER = "testdata/";

    private TestDataUtils() {
    }

    public static Properties load(String fileName) {
        String resource = FOLDER + fileName;
        try (InputStream input = threadClassLoader().getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalStateException("Test data not found on classpath: " + resource);
            }
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new UncheckedIOException("Unable to load test data: " + resource, exception);
        }
    }

    public static String get(Properties data, String key) {
        String value = data.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing test data key: " + key);
        }
        return value.trim();
    }

    private static ClassLoader threadClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return Objects.requireNonNullElse(loader, TestDataUtils.class.getClassLoader());
    }
}
