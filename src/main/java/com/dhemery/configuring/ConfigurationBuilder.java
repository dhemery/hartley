package com.dhemery.configuring;

import com.dhemery.core.Builder;

import java.util.*;

/**
 * Builds configurations.
 */
public class ConfigurationBuilder implements Builder<Configuration> {
    private final Configuration configuration;
    private final Configuration overrides = configuration(backedBy(new HashMap<String, String>()));

    private ConfigurationBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Build a configuration.
     */
    public static ConfigurationBuilder newBuilder() {
        return into(new HashMap<String, String>());
    }

    /**
     * Build a configuration that stores options in the given configuration.
     */
    public static ConfigurationBuilder into(Configuration configuration) {
        return new ConfigurationBuilder(configuration);
    }

    /**
     * Build a configuration that stores options in the given map.
     */
    public static ConfigurationBuilder into(Map<String,String> map) {
        return new ConfigurationBuilder(configuration(backedBy(map)));
    }

    /**
     * Build a configuration that stores options in the given properties.
     */
    public static ConfigurationBuilder into(Properties properties) {
        return new ConfigurationBuilder(configuration(backedBy(properties)));
    }

    /**
     * Merge properties from the named file into the configuration.
     */
    public ConfigurationBuilder fromFile(String fileName) {
        return fromFiles(fileName);
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
        return fromResources(resourceName);
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
//    public ConfigurationBuilder withOptionValues(OptionFilter... filters) {
//        this.filters.addAll(Arrays.asList(filters));
//        return this;
//    }

    @Override
    public Configuration build() {
        configuration.merge(overrides);
        return configuration;
    }

    private static ModifiableOptionStore backedBy(Map<String, String> map) {
        return new MapBackedOptionStore(map);
    }

    private static PropertiesBackedOptionStore backedBy(Properties properties) {
        return new PropertiesBackedOptionStore(properties);
    }

    private static StoreBackedConfiguration configuration(ModifiableOptionStore store) {
        return new StoreBackedConfiguration(store);
    }
}
