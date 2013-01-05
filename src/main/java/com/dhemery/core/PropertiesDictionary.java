package com.dhemery.core;

import java.util.Properties;
import java.util.Set;

/**
 * A dictionary stored in a {@link Properties}.
 * Changes to the dictionary are written through to the underlying properties.
 * Properties in the underlying properties are reflected in the dictionary
 */
public class PropertiesDictionary implements StringDictionary {
    private final Properties properties;

    /**
     * Create an empty dictionary stored in a new {@code Properties}.
     */
    public PropertiesDictionary() {
        this(new Properties());
    }

    /**
     * Create a dictionary stored in the given properties.
     */
    public PropertiesDictionary(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void define(String term, String definition) {
        properties.setProperty(term, definition);
    }

    @Override
    public String definitionOf(String term) {
        return properties.getProperty(term);
    }

    @Override
    public Set<String> terms() {
        return properties.stringPropertyNames();
    }
}
