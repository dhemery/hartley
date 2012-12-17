package com.dhemery.configuring;

import java.util.Properties;
import java.util.Set;

/**
 * A configuration that uses a Properties to store and retrieve options.
 * Changes to the configuration are written to the properties.
 * Changes to the properties are reflected in configuration queries.
 */
public class PropertiesBackedConfiguration extends AbstractConfiguration {
    private final Properties properties;

    /**
     * Create a configuration with no options defined.
     */
    public PropertiesBackedConfiguration() {
        this(new Properties());
    }

    /**
     * Create a configuration that uses the given properties to store and retrieve options.
     */
    public PropertiesBackedConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void define(String name, String value) {
        properties.setProperty(name, value);
    }

    @Override
    public boolean defines(String name) {
        return names().contains(name);
    }

    @Override
    public Set<String> names() {
        return properties.stringPropertyNames();
    }

    @Override
    public String option(String name) {
        return properties.getProperty(name);
    }
}
