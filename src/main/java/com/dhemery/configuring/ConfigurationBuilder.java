package com.dhemery.configuring;

import com.dhemery.core.Builder;

import java.util.*;

/**
 * A builder that builds configurations.
 */
public class ConfigurationBuilder implements Builder<Configuration> {
    private final List<OptionFilter> filters = new ArrayList<OptionFilter>();
    private final Configuration configuration;
    private final Configuration overrides = new MapBackedConfiguration();

    private ConfigurationBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Create a new builder that builds into an empty {@link MapBackedConfiguration}.
     */
    public static ConfigurationBuilder newBuilder() {
        return into(new MapBackedConfiguration());
    }

    /**
     * Create a new builder that builds into the given configuration.
     */
    public static ConfigurationBuilder into(Configuration configuration) {
        return new ConfigurationBuilder(configuration);
    }

    /**
     * Merge properties from the named file into the configuration.
     */
    public ConfigurationBuilder fromFile(String fileName) {
        LoadProperties.fromFile(fileName).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named files into the configuration.
     */
    public ConfigurationBuilder fromFiles(String... fileNames) {
        LoadProperties.fromFiles(fileNames).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named resource into the configuration.
     */
    public ConfigurationBuilder fromResource(String resourceName) {
        LoadProperties.fromResource(resourceName).into(configuration);
        return this;
    }

    /**
     * Merge properties from the named resources into the configuration.
     */
    public ConfigurationBuilder fromResources(String... resourceNames) {
        LoadProperties.fromResources(resourceNames).into(configuration);
        return this;
    }

    /**
     * Merge another configuration's options into the configuration.
     */
    public ConfigurationBuilder from(Configuration configuration) {
        overrides.merge(configuration);
        return this;
    }

    /**
     * Merge map entries from the given map into the configuration.
     */
    public ConfigurationBuilder from(Map<String,String> map) {
        overrides.merge(map);
        return this;
    }

    /**
     * Merge the given properties into the configuration.
     */
    public ConfigurationBuilder from(Properties properties) {
        overrides.merge(properties);
        return this;
    }

    /**
     * Append option value filters onto the configuration's filter list.
     */
    public ConfigurationBuilder withOptionValues(OptionFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
        return this;
    }

    @Override
    public Configuration build() {
        configuration.merge(overrides);
        if(filters.isEmpty()) return configuration;
        return new FilteringConfiguration(configuration, filters);
    }
}
