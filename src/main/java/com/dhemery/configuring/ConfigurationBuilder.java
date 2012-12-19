package com.dhemery.configuring;

import com.dhemery.core.Builder;

import java.util.*;

import static com.dhemery.configuring.Copy.copy;

/**
 * Builds configurations.
 * Each configuration is constructed from three parts:
 * <ul>
 * <ol>A backing store that the configuration uses to store options</ol>
 * <ol>A set of overrides to merge into the configuration</ol>
 * <ol>A set of filters for the configuration to apply
 * when its {@code option()} methods are called</ol>
 * </ul>
 * <p>Each {@code into} method creates a builder and supplies a backing store
 * to be used by the constructed configuration.
 * The backing store is not altered until the {@code build} method is called.
 * </p>
 * <p>Each {@code merge} method merges options from a given source into
 * a set of overrides.
 * If multiple merged sources define an option,
 * the overrides retain the value from the last defining source to be merged.
 * When {@code build()} is called,
 * these overrides are merged into the constructed configuration.
 * </p>
 */
public class ConfigurationBuilder implements Builder<Configuration> {
    private final ModifiableOptions baseOptions;
    private final Map<String,String> overrides = new HashMap<String, String>();
    private List<OptionFilter> filters = new ArrayList<OptionFilter>();

    private ConfigurationBuilder(ModifiableOptions baseOptions) {
        this.baseOptions = baseOptions;
    }

    /**
     * Create a builder to build a configuration into a new, empty backing store.
     */
    public static ConfigurationBuilder intoNewConfiguration() {
        return into(new HashMap<String, String>());
    }

    /**
     * Create a builder to build a configuration into the given map.
     */
    public static ConfigurationBuilder into(Map<String,String> map) {
        return new ConfigurationBuilder(new MappedOptions(map));
    }

    /**
     * Create a builder to build a configuration into the given {@link ModifiableOptions}.
     */
    public static ConfigurationBuilder into(ModifiableOptions options) {
        return new ConfigurationBuilder(options);
    }

    /**
     * Create a builder to build a configuration into the given properties.
     */
    public static ConfigurationBuilder into(Properties properties) {
        return new ConfigurationBuilder(new PropertiesOptions(properties));
    }

    /**
     * Merge properties from the named file into the overrides for the configuration.
     */
    public ConfigurationBuilder mergeFile(String fileName) {
        return mergeFiles(fileName);
    }

    /**
     * Merge properties from the named files into the overrides for the configuration.
     */
    public ConfigurationBuilder mergeFiles(String... fileNames) {
        LoadProperties.fromFiles(fileNames).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named resource into the overrides for the configuration.
     */
    public ConfigurationBuilder mergeResource(String resourceName) {
        return mergeResources(resourceName);
    }

    /**
     * Merge properties from the named resources into the overrides for the configuration.
     */
    public ConfigurationBuilder mergeResources(String... resourceNames) {
        LoadProperties.fromResources(resourceNames).into(overrides);
        return this;
    }

    /**
     * Merge options into the overrides for the configuration.
     */
    public ConfigurationBuilder merge(Options options) {
        copy(options).into(overrides);
        return this;
    }

    /**
     * Merge a map into the overrides for the configuration.
     */
    public ConfigurationBuilder merge(Map<String, String> map) {
        copy(map).into(overrides);
        return this;
    }

    /**
     * Merge properties into the overrides for the configuration.
     */
    public ConfigurationBuilder merge(Properties properties) {
        copy(properties).into(overrides);
        return this;
    }

    /**
     * Append option filters onto the configuration's filter list.
     */
    public ConfigurationBuilder withOptionValues(OptionFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
        return this;
    }

    /**
     * Construct the configuration specified by the builder.
     * This creates a new configuration backed by the specified backing store,
     * attaches the specified filters.
     * and merges the specified overrides into the newly constructed configuration,
     * @return the constructed configuration
     */
    @Override
    public Configuration build() {
        copy(overrides).into(baseOptions);
        ModifiableOptions filteredOptions = new FilteringOptions(baseOptions, filters);
        return new OptionsBackedConfiguration(filteredOptions);
    }
}
