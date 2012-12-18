package com.dhemery.configuring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A {@link Configuration} backed by a {@link ModifiableOptions}.
 * Changes to this configuration are written through to the underlying options.
 * Changes in the underlying options are reflected in queries of this configuration.
 */
public class OptionsBackedConfiguration implements Configuration {
    private final ModifiableOptions options;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions(new HashMap<String, String>()));
    }

    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options) {
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
    public void merge(Options options) {
        for(String name : options.names()) define(name, options.option(name));
    }

    @Override
    public void merge(Map<String, String> map) {
        for(Map.Entry<String, String> entry : map.entrySet()) define(entry.getKey(), entry.getValue());
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
    public String option(String name, OptionFilter... filters) {
        return new FilteringOptions(options, filters).option(name);
    }

    @Override
    public String requiredOption(String name) {
        if(defines(name)) return option(name);
        throw new ConfigurationException("Undefined option " + name);
    }
}
