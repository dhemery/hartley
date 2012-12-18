package com.dhemery.configuring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StoreBackedConfiguration implements Configuration {
    private final ModifiableOptions options;

    public StoreBackedConfiguration(ModifiableOptions options) {
        this.options = options;
    }

    @Override
    public Map<String, String> asMap() {
        Map<String,String> map = new HashMap<String, String>();
        for(String name : names()) map.put(name, option(name));
        return map;
    }

    @Override
    public Properties asProperties() {
        Properties copy = new Properties();
        for(String name : names()) copy.setProperty(name, option(name));
        return copy;
    }

    @Override
    public void define(String name, String value) {
        options.define(name, value);
    }

    @Override
    public boolean defines(String name) {
        return options.names().contains(name);
    }

    @Override
    public void merge(Configuration source) {
        for(String name : source.names()) define(name, source.option(name));
    }

    @Override
    public void merge(Map<String, String> map) {
        for(Map.Entry<String, String> option : map.entrySet()) define(option.getKey(), option.getValue());
    }

    @Override
    public void merge(Properties properties) {
        for(String name : properties.stringPropertyNames()) define(name, properties.getProperty(name));
    }

    @Override
    public Set<String> names() {
        return options.names();
    }

    @Override
    public String option(String name) {
        return options.option(name);
    }

    @Override
    public String requiredOption(String name) {
        if(defines(name)) return option(name);
        throw new ConfigurationException("Undefined option " + name);
    }
}
