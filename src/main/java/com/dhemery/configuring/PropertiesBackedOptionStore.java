package com.dhemery.configuring;

import java.util.*;

public class PropertiesDictionary implements ModifiableDictionary {
    private final Properties properties;

    public PropertiesBackedOptionStore() {
        this(new Properties());
    }

    public PropertiesBackedOptionStore(Properties properties) {
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
    public String retrieve(String name) {
        return properties.getProperty(name);
    }
}
