package com.dhemery.configuring;

import com.dhemery.configuring.filtering.OptionFilterList;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.dhemery.configuring.OptionExpressions.required;

public abstract class AbstractConfiguration implements Configuration {
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
    public String option(String name, OptionFilter... filters) {
        return filteredOption(name, option(name), new OptionFilterList(filters));
    }

    @Override
    public String requiredOption(String name) {
        return option(name, required());
    }

    protected String filteredOption(String name, String value, OptionFilter filter) {
        Option option = new Option(this, name, value);
        return filter.of(option);
    }
}
