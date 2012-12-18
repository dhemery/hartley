package com.dhemery.configuring;

import com.dhemery.core.Builder;

import java.util.*;

/**
 * Builds configurations.
 */
public class ConfigurationBuilder implements Builder<Configuration> {
    private final ModifiableOptions baseOptions;
    private final ModifiableOptions overrideOptions = backedBy(new HashMap<String, String>());
    private final Configuration overrides = configuration(overrideOptions);
    private List<OptionFilter> filters = new ArrayList<OptionFilter>();

    private ConfigurationBuilder(ModifiableOptions baseOptions) {
        this.baseOptions = baseOptions;
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
        return new ConfigurationBuilder(backedBy(map));
    }

    /**
     * Build a configuration that stores options in the given properties.
     */
    public static ConfigurationBuilder into(Properties properties) {
        return new ConfigurationBuilder(backedBy(properties));
    }

    /**
     * Merge properties from the named file into the configuration.
     */
    public ConfigurationBuilder overriddenByFile(String fileName) {
        return overriddenByFiles(fileName);
    }

    /**
     * Merge properties from the named files into the configuration.
     */
    public ConfigurationBuilder overriddenByFiles(String... fileNames) {
        LoadProperties.fromFiles(fileNames).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named resource into the configuration.
     */
    public ConfigurationBuilder overriddenByResource(String resourceName) {
        return overriddenByResources(resourceName);
    }

    /**
     * Merge properties from the named resources into the configuration.
     */
    public ConfigurationBuilder overriddenByResources(String... resourceNames) {
        LoadProperties.fromResources(resourceNames).into(overrides);
        return this;
    }

    /**
     * Merge another configuration's options into the configuration.
     */
    public ConfigurationBuilder overriddenBy(Configuration configuration) {
        overrides.merge(configuration);
        return this;
    }

    /**
     * Merge map entries from the given map into the configuration.
     */
    public ConfigurationBuilder overriddenBy(Map<String,String> map) {
        overrides.merge(map);
        return this;
    }

    /**
     * Merge the given properties into the configuration.
     */
    public ConfigurationBuilder overriddenBy(Properties properties) {
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
        ModifiableOptions filteredOptions = new FilteringOptions(baseOptions, filters);
        Configuration configuration = new DefaultConfiguration(filteredOptions);
        configuration.merge(overrides);
        return configuration;
    }

    private static ModifiableOptions backedBy(Map<String, String> map) {
        return new MapBackedOptions(map);
    }

    private static PropertiesBackedOptions backedBy(Properties properties) {
        return new PropertiesBackedOptions(properties);
    }

    private static DefaultConfiguration configuration(ModifiableOptions options) {
        return new DefaultConfiguration(options);
    }
}
