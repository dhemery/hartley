package com.dhemery.configuring;

import com.dhemery.core.Builder;
import com.dhemery.core.UnaryOperator;

import java.util.*;

import static com.dhemery.configuring.Copy.copy;
import static com.dhemery.configuring.LoadProperties.propertiesFromFiles;
import static com.dhemery.configuring.LoadProperties.propertiesFromResources;

/**
 * Builds configurations.
 * Each configuration is constructed from three parts:
 * <ul>
 * <ol>A backing store that the configuration uses to store options</ol>
 * <ol>A set of overrides to merge into the configuration</ol>
 * <ol>A set of operators for the configuration to apply whenever an option is queried
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
    private String name = "(unnamed configuration)";
    private List<UnaryOperator<String>> operators = new ArrayList<UnaryOperator<String>>();
    private ModifiableOptions options;
    private final Map<String,String> overrides = new HashMap<String, String>();

    private ConfigurationBuilder() {
        options = new MappedOptions();
    }

    /**
     * Create a builder to build a new configuration backed by an empty map
     * and with an empty list of operators.
     */
    public static ConfigurationBuilder newConfiguration() {
        return new ConfigurationBuilder();
    }

    /**
     * Set the given map as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedByMap(Map<String,String> map) {
        options = new MappedOptions(map);
        return this;
    }

    /**
     * Set the given {@code ModifiableOptions} as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedByOptions(ModifiableOptions options) {
        this.options = options;
        return this;
    }

    /**
     * Set the given properties as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedByProperties(Properties properties) {
        options = new PropertiesOptions(properties);
        return this;
    }

    /**
     * Merge properties from the named file into the overrides for the configuration.
     */
    public ConfigurationBuilder withPropertiesFromFile(String fileName) {
        return withPropertiesFromFiles(fileName);
    }

    /**
     * Merge properties from the named files into the overrides for the configuration.
     */
    public ConfigurationBuilder withPropertiesFromFiles(String... fileNames) {
        copy(propertiesFromFiles(fileNames)).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named resource into the overrides for the configuration.
     */
    public ConfigurationBuilder withPropertiesFromResource(String resourceName) {
        return withPropertiesFromResources(resourceName);
    }

    /**
     * Merge properties from the named resources into the overrides for the configuration.
     */
    public ConfigurationBuilder withPropertiesFromResources(String... resourceNames) {
        copy(propertiesFromResources(resourceNames)).into(overrides);
        return this;
    }

    /**
     * Merge options into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFrom(Options options) {
        copy(options).into(overrides);
        return this;
    }

    /**
     * Merge map entries into the overrides for the configuration.
     */
    public ConfigurationBuilder withEntriesFrom(Map<String, String> map) {
        copy(map).into(overrides);
        return this;
    }

    /**
     * Merge properties into the overrides for the configuration.
     */
    public ConfigurationBuilder withPropertiesFrom(Properties properties) {
        copy(properties).into(overrides);
        return this;
    }

    /**
     * Set the name for the configuration.
     */
    public ConfigurationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Append the operators to the list of operators that the configuration will apply whenever an option is queried.
     */
    public ConfigurationBuilder withOptionsTransformedBy(UnaryOperator<String>... operators) {
        this.operators.addAll(Arrays.asList(operators));
        return this;
    }

    /**
     * Construct the configuration specified by the builder.
     * This creates a new configuration backed by the specified backing store,
     * and merges the specified overrides into the newly constructed configuration,
     * @return the constructed configuration
     */
    @Override
    public Configuration build() {
        copy(overrides).into(options);
        return new OptionsBackedConfiguration(name, options, operators);
    }
}
