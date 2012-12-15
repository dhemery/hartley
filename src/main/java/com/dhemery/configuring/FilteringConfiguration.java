package com.dhemery.configuring;

import java.util.*;

public class FilteringConfiguration implements Configuration {
    private final List<OptionFilter> filters = new ArrayList<OptionFilter>();
    private final Configuration configuration;

    public FilteringConfiguration(Configuration configuration, List<OptionFilter> filters) {
        this(configuration);
        this.filters.addAll(filters);
    }

    public FilteringConfiguration(Configuration configuration, OptionFilter filter) {
        this(configuration);
        filters.add(filter);
    }

    public FilteringConfiguration(Configuration configuration) {
        this.configuration = configuration;
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
        return filter(name, configuration.option(name));
    }

    @Override
    public String option(String name, OptionFilter... userFilters) {
        return filter(name, configuration.option(name, userFilters));
    }

    @Override
    public String requiredOption(String name) {
        return filter(name, configuration.requiredOption(name));
    }

    private String filter(String name, String value) {
        Option option = new Option(this, name, value);
        option.apply(filters);
        return option.value();
    }
}
