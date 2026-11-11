package com.company.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

/**
 * Loads classpath {@code config.properties}. JVM {@code -D} properties override file values
 * so CI can change browser or timeouts without editing files.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();
    private static volatile boolean loaded;

    private ConfigReader() {
    }

    public static synchronized void load() {
        try (InputStream input = threadClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException(CONFIG_FILE + " was not found on the classpath");
            }
            PROPERTIES.clear();
            PROPERTIES.load(input);
            loaded = true;
        } catch (IOException exception) {
            throw new UncheckedIOException("Unable to load " + CONFIG_FILE, exception);
        }
    }

    public static String get(String key) {
        ensureLoaded();
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem.trim();
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration key: " + key);
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static String getSalesforceBaseUrl() {
        return get("salesforce.baseUrl");
    }

    public static String getSauceBaseUrl() {
        return get("saucedemo.baseUrl");
    }

    public static String getSauceUsername() {
        return get("saucedemo.username");
    }

    public static String getSaucePassword() {
        return get("saucedemo.password");
    }

    public static String getSalesforceUsername() {
        String username = System.getenv("SALESFORCE_USERNAME");
        if (username == null || username.isBlank()) {
            throw new IllegalStateException("Environment variable SALESFORCE_USERNAME is not set");
        }
        return username;
    }

    public static String getSalesforcePassword() {
        String password = System.getenv("SALESFORCE_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Environment variable SALESFORCE_PASSWORD is not set");
        }
        return password;
    }

    public static Duration getDurationSeconds(String key) {
        return Duration.ofSeconds(getInt(key));
    }

    private static void ensureLoaded() {
        if (!loaded) {
            load();
        }
    }

    private static ClassLoader threadClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return Objects.requireNonNullElse(loader, ConfigReader.class.getClassLoader());
    }
}
