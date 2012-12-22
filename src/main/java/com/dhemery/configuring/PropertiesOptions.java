package com.dhemery.configuring;

import java.util.Properties;
import java.util.Set;

/**
 * A set of options backed by a {@link Properties}.
 * Changes to the options are written through to the underlying properties.
 * Changes to the underlying properties are reflected in queries to this set's options.
 */
public class PropertiesOptions implements ModifiableOptions {
    private final Properties properties;

    /**
     * Create a set of options backed by an empty {@code Properties}.
     */
    public PropertiesOptions() {
        this(new Properties());
    }

    /**
     * Create a set of options backed by the given properties.
     */
    public PropertiesOptions(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void define(String name, String value) {
        properties.setProperty(name, value);
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
