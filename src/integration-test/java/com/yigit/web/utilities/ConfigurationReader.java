package com.yigit.web.utilities;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ConfigurationReader {
    private static final Properties properties = new Properties();
    /**
     * Calls data from configuration.properties class
     *
     * @param keyName data from configuration.properties
     * @return properties or null if not found
     */
    public static String get(String keyName) {
        try (InputStream input = ConfigurationReader.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            properties.load(input);
            return properties.getProperty(keyName);
        } catch (IOException e) {
            log.error("Err", e);
        }
        return null;
    }

    /**
     * Calls data from configuration.properties class
     *
     * Usage: addBrowserOptions=--headless,--verbose
     * @param keyNames data --headless,--verbose,--allow-insecure-localhost,--disable-dev-shm-usage,--no-sandbox,--disable-gpu can be used
     * @return properties or null if not found
     */
    public static List<String> getValues(String keyNames) {
        try (InputStream input = ConfigurationReader.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            properties.load(input);
            String list = properties.getProperty(keyNames);
            return new ArrayList<>(Arrays.asList(list.split(",")));
        } catch (IOException e) {
            log.error("Err", e);
        }
        return null;
    }
}
