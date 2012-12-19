package com.dhemery.configuring;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Static utility methods to load properties from property files and resources.
 */
public class LoadProperties {
    /**
     * Load properties from a properties file.
     * @param fileName the name of the properties file to load
     * @throws ConfigurationException   if the properties file does not exist or an IO exception occurs while loading properties
     */
    public static Properties propertiesFromFile(String fileName) {
        return propertiesFromFiles(fileName);
    }

    /**
     * Load properties from properties files in the order that the file names
     * are listed. If multiple files define a property, the value loaded last is
     * retained.
     *
     * @param fileNames the names of the properties files to load
     * @throws ConfigurationException   if a properties file does not exist or an IO exception occurs while loading properties
     */
    public static Properties propertiesFromFiles(String... fileNames) {
        return propertiesFrom(streamsForFiles(fileNames));
    }

    /**
     * Load properties from a properties resources.
     * <p>The resource name must be an absolute name.  That is, it must start with a <tt>/</tt> character.</p>
     *
     * @param resourceName the name of the properties resource to load
     * @throws ConfigurationException   if the properties resource does not exist or an IO exception occurs while loading properties
     */
    public static Properties propertiesFromResource(String resourceName) {
        return propertiesFromResources(resourceName);
    }

    /**
     * Load properties from properties resources in the order that the resource names
     * are listed. If multiple resources define a property, the value loaded last is
     * retained.
     * <p>Each resource name must be an absolute name.  That is, it must start with a <tt>/</tt> character.</p>
     *
     * @param resourceNames the names of the properties resources to load
     * @throws ConfigurationException   if a properties resource does not exist or an IO exception occurs while loading properties
     */
    public static Properties propertiesFromResources(String... resourceNames) {
        return propertiesFrom(streamsForResources(resourceNames));
    }

    private static Properties propertiesFrom(List<InputStream> streams) {
        Properties properties = new Properties();
        for (InputStream stream : streams) {
            loadPropertiesFromStream(properties, stream);
        }
        return properties;
    }

    private static void loadPropertiesFromStream(Properties properties, InputStream stream) {
        try {
            properties.load(stream);
            stream.close();
        } catch (IOException cause) {
            throw new ConfigurationException("IO Exception while reading properties from " + stream, cause);
        }
    }

    private static InputStream streamForFile(String name) {
        try {
            return new FileInputStream(name);
        } catch (FileNotFoundException cause) {
            throw new ConfigurationException("Cannot find properties file " + name, cause);
        }
    }

    private static List<InputStream> streamsForFiles(String... names) {
        List<InputStream> streams = new ArrayList<InputStream>();
        for(String name : names) {
            streams.add(streamForFile(name));
        }
        return streams;
    }

    private static InputStream streamForResource(String name) {
        InputStream stream = LoadProperties.class.getResourceAsStream(name);
        if(stream == null) throw new ConfigurationException("Cannot find properties resource " + name);
        return stream;
    }

    private static List<InputStream> streamsForResources(String... names) {
        List<InputStream> streams = new ArrayList<InputStream>();
        for(String name : names) {
            streams.add(streamForResource(name));
        }
        return streams;
    }
}
