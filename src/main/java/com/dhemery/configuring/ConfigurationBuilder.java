package com.dhemery.configuring;

import com.dhemery.core.*;
import com.dhemery.core.StringDictionary;

import java.util.*;

import static com.dhemery.configuring.LoadProperties.propertiesFromFiles;
import static com.dhemery.configuring.LoadProperties.propertiesFromResources;

/**
 * Builds transforming configurations.
 * Each transforming configuration is constructed from three parts:
 * <ul>
 * <ol>A backing store that the configuration uses to store options</ol>
 * <ol>A set of options to merge into the configuration</ol>
 * <ol>A set of operators for the configuration to apply whenever an option is queried
 * </ul>
 * <p>Each {@code backedBy} method supplies a backing store
 * to be used by the constructed configuration.
 * The supplied backing store supersedes any previously supplied backing store.
 * The backing store's contents are not altered until the {@code build} method is called.
 * </p>
 * <p>Each {@code withOptions} method merges options from a given source into
 * a set of overrides.
 * If multiple sources define an option,
 * the overrides retain the value from the last defining source to be merged.
 * When {@code build()} is called,
 * these overrides are merged into the backing store.
 * </p>
 */
public class ConfigurationBuilder implements Builder<Configuration> {
    private String name = "(unnamed configuration)";
    private StringDictionary dictionary;
    private final List<UnaryOperator<String>> operators = new ArrayList<UnaryOperator<String>>();
    private final Map<String,String> overrides = new HashMap<String, String>();

    private ConfigurationBuilder() {
        dictionary = new MappedDictionary();
    }

    /**
     * Create a builder to build a new configuration backed by an empty map
     * and with an empty list of operators.
     */
    public static ConfigurationBuilder newConfiguration() {
        return new ConfigurationBuilder();
    }

    /**
     * Append the operators to the list of operators that the configuration will apply whenever an option is queried.
     */
    public ConfigurationBuilder applying(UnaryOperator<String>... operators) {
        this.operators.addAll(Arrays.asList(operators));
        return this;
    }

    /**
     * Set the given {@code Dictioanry} as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedBy(StringDictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    /**
     * Set the given map as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedBy(Map<String,String> map) {
        dictionary = new MappedDictionary(map);
        return this;
    }

    /**
     * Set the given properties as the backing store for the configuration.
     * Any previously configured backing store is forgotten.
     */
    public ConfigurationBuilder backedBy(Properties properties) {
        dictionary = new PropertiesDictionary(properties);
        return this;
    }

    /**
     * Construct the configuration specified by the builder.
     * This creates a new configuration backed by the specified backing store,
     * and merges the specified overrides into the newly constructed configuration,.
     * @return the constructed configuration
     */
    @Override
    public Configuration build() {
        CopyTerms.from(overrides).into(dictionary);
        return new TransformingConfiguration(name, dictionary, operators);
    }

    /**
     * Set the name for the configuration.
     */
    public ConfigurationBuilder named(String name) {
        this.name = name;
        return this;
    }

    /**
     * Merge options into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFrom(StringDictionary dictionary) {
        CopyTerms.from(dictionary).into(overrides);
        return this;
    }

    /**
     * Merge map entries into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFrom(Map<String, String> map) {
        CopyTerms.from(map).into(overrides);
        return this;
    }

    /**
     * Merge properties into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFrom(Properties properties) {
        CopyTerms.from(properties).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named file into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFromPropertiesFile(String fileName) {
        return withOptionsFromPropertiesFiles(fileName);
    }

    /**
     * Merge properties from the named files into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFromPropertiesFiles(String... fileNames) {
        CopyTerms.from(propertiesFromFiles(fileNames)).into(overrides);
        return this;
    }

    /**
     * Merge properties from the named resource into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFromPropertiesResource(String resourceName) {
        return withOptionsFromPropertiesResources(resourceName);
    }

    /**
     * Merge properties from the named resources into the overrides for the configuration.
     */
    public ConfigurationBuilder withOptionsFromPropertiesResources(String... resourceNames) {
        CopyTerms.from(propertiesFromResources(resourceNames)).into(overrides);
        return this;
    }
}
