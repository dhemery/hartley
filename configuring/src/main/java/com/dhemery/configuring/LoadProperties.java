package com.dhemery.configuring;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Loads properties from one or more property files
 * and deliver the properties in a variety of representations.
 */
public class LoadProperties {
    private final Properties properties;

    /**
     * Load properties from property files in the order that the file names
     * are listed. If multiple files define a property, the value loaded last is
     * retained.
     *
     * @param propertyFileNames the names of the files from which to read properties
     * @throws ConfigurationException   if a property file does not exist or an IO exception occurs while loading properties
     */
    public static LoadProperties fromFiles(String... propertyFileNames) {
        return new LoadProperties(propertyFileNames);
    }

    private LoadProperties(String... propertyFileNames) {
        properties = new Properties();
        for (String filename : propertyFileNames) {
            loadPropertiesFromFile(filename);
        }
    }

    /**
     * Copy the loaded properties into a configuration.
     */
    public void into(Configuration configuration) {
        configuration.merge(properties);
    }

    /**
     * Copy the loaded properties into a map.
     */
    public void into(Map<String, String> map) {
        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
    }

    /**
     * Copy the loaded properties into a properties list.
     */
    public void into(Properties properties) {
        for (String name : this.properties.stringPropertyNames()) {
            properties.setProperty(name, this.properties.getProperty(name));
        }
    }

    private void loadPropertiesFromFile(String filename) {
        try {
            InputStream propertiesFile = new FileInputStream(filename);
            properties.load(propertiesFile);
            propertiesFile.close();
        } catch (FileNotFoundException cause) {
            throw new ConfigurationException("Cannot find properties file " + filename, cause);
        } catch (IOException cause) {
            throw new ConfigurationException("IO Exception while reading properties from " + filename, cause);
        }
    }
}
