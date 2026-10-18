package com.company.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Optional;

/**
 * ConfigReader utility class for loading application configuration.
 * Supports properties file, system property overrides, and environment variables for sensitive data.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config/config.properties";
    private static final Properties properties = new Properties();
    private static boolean isLoaded = false;

    private ConfigReader() {
        // Private constructor to prevent instantiation
    }

    /**
     * Loads properties from the configuration file.
     */
    public static synchronized void load() {
        if (isLoaded) {
            return;
        }
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + CONFIG_FILE + " on the classpath");
            }
            properties.load(input);
            isLoaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
        }
    }

    /**
     * Gets a configuration value based on priority: System Property > Properties File.
     *
     * @param key The configuration key.
     * @return The resolved configuration value.
     * @throws IllegalArgumentException if the key is not found in either source.
     */
    public static String getProperty(String key) {
        load();

        // 1. Check System Properties (-Dkey=value)
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        // 2. Check Properties File
        value = properties.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        throw new IllegalArgumentException("Missing required configuration: " + key +
            ". Please provide it in " + CONFIG_FILE + " or as a system property.");
    }

    /**
     * Gets a value from environment variables. Used for sensitive data.
     *
     * @param key The environment variable name.
     * @return The resolved environment variable value.
     * @throws IllegalArgumentException if the environment variable is not set.
     */
    public static String getEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required environment variable: " + key);
        }
        return value.trim();
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    /**
     * Example methods for credentials as per requirements.
     * These strictly pull from environment variables.
     */
    public static String getUsername() {
        return getEnv("SF_USERNAME");
    }

    public static String getPassword() {
        return getEnv("SF_PASSWORD");
    }
}
