package com.terrelloAPI.utils.dataReader;


import com.terrelloAPI.utils.logs.LogsManger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    public static Properties loadProperties() {
        System.out.println("=== DEBUG: scanning src/main/resources exists: " + new File("src/main/resources").exists());
        System.out.println("=== DEBUG: scanning src/test/resources exists: " + new File("src/test/resources").exists());
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFiles = FileUtils.listFiles(
                    new File("src/main/resources"),
                    new String[]{"properties"},
                    true
            );

            propertiesFiles.forEach(file -> {
                try {
                    properties.load(FileUtils.openInputStream(file));
                } catch (Exception e) {
                    com.terrelloAPI.utils.logs.LogsManger.info("Error loading properties", file.getName(), e.getMessage());
                }
            });

            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);

            return properties;
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error loading properties file", e.getMessage());
            return null;
        }
    }
    public static String getProperty(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error getting property for key:", key, e.getMessage());
            return "";
        }
    }
}
