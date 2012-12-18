package com.dhemery.configuring;

import java.util.*;

public class PropertiesBackedOptions implements ModifiableOptions{
    private final Properties properties;

    public PropertiesBackedOptions() {
        this(new Properties());
    }

    public PropertiesBackedOptions(Properties properties) {
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
