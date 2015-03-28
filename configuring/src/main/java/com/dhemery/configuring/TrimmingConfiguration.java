package com.dhemery.configuring;

import com.dhemery.core.Supplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Decorates another configuration to trim whitespace from the values of its options.
 * The values in the underlying configuration are not changed.
 * The values are trimmed each time a user reads an option,
 * and each time the trimming configuration is converted to a map or a properties list.
 */
public class TrimmingConfiguration implements Configuration {
    private final Configuration configuration;

    public TrimmingConfiguration(Configuration untrimmed) {
        configuration = untrimmed;
    }

    @Override
    public Map<String, String> asMap() {
        Map<String,String> map = new HashMap<String, String>();
        for(String name : names()) map.put(name, option(name));
        return map;
    }

    @Override
    public Properties asProperties() {
        Properties properties = new Properties();
        for(String name : names()) properties.setProperty(name, option(name));
        return properties;
    }

    @Override
    public void define(String name, String value) {
        configuration.define(name, value);
    }

    @Override
    public Boolean defines(String name) {
        return configuration.defines(name);
    }

    @Override
    public void merge(Configuration other) {
        configuration.merge(other);
    }

    @Override
    public void merge(Map<String, String> map) {
        configuration.merge(map);
    }

    @Override
    public void merge(Properties properties) {
        configuration.merge(properties);
    }

    @Override
    public Set<String> names() {
        return configuration.names();
    }

    @Override
    public String option(String name) {
        return safelyTrim(configuration.option(name));
    }

    @Override
    public String option(String name, String defaultValue) {
        return safelyTrim(configuration.option(name, defaultValue));
    }

    @Override
    public String option(String name, Supplier<String> supplier) {
        return safelyTrim(configuration.option(name, supplier));
    }

    @Override
    public String requiredOption(String name) {
        return safelyTrim(configuration.requiredOption(name));
    }

    private static String safelyTrim(String value) {
        return value == null ? null : value.trim();
    }
}
