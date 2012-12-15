package com.dhemery.configuring;

import java.util.*;

import static com.dhemery.configuring.OptionExpressions.required;

/**
 * A set of configuration options backed by a {@link Map}.
 */
public class MapBackedConfiguration implements Configuration {
    private final Map<String, String> options = new HashMap<String, String>();

    /**
     * Create a configuration with no options defined.
     */
    public MapBackedConfiguration() {
    }


    /**
     * Create a configuration by copying options from another configuration.
     */
    public MapBackedConfiguration(Configuration other) {
        this(other.asMap());
    }

    /**
     * Create a configuration by copying options from a map.
     */
    public MapBackedConfiguration(Map<String, String> map) {
        merge(map, options);
    }

    /**
     * Create a configuration by copying options from a property list.
     */
    public MapBackedConfiguration(Properties properties) {
        merge(properties, options);
    }

    @Override
    public Map<String, String> asMap() {
        return new HashMap<String,String>(options);
    }

    @Override
    public Properties asProperties() {
        Properties properties = new Properties();
        for(String name : options.keySet()) properties.setProperty(name, option(name));
        return properties;
    }

    @Override
    public void define(String name, String value) {
        options.put(name, value);
    }

    @Override
    public Boolean defines(String name) {
        return options.containsKey(name);
    }

    @Override
    public void merge(Configuration other) {
        merge(other.asMap());
    }

    @Override
    public void merge(Map<String, String> map) {
        merge(map, options);
    }

    @Override
    public void merge(Properties properties) {
        merge(properties, options);
    }

    @Override
    public Set<String> names() {
        return new HashSet<String>(options.keySet());
    }

    @Override
    public String option(String name) {
        return options.get(name);
    }

    @Override
    public String option(String name, OptionFilter... filters) {
        Option option = new Option(this, name, option(name));
        option.apply(Arrays.asList(filters));
        return option.value();
    }

    @Override
    public String requiredOption(String name) {
        return option(name, required());
    }

    private static void merge(Map<String, String> map, Map<String, String> options) {
        options.putAll(map);
    }

    private static void merge(Properties properties, Map<String, String> options) {
        for(String propertyName : properties.stringPropertyNames()) {
            options.put(propertyName, properties.getProperty(propertyName));
        }
    }
}
