package com.dhemery.configuring;

import com.dhemery.core.Supplier;

import java.util.*;

/**
 * A set of configuration options.
 */
public class Configuration {
    private final Map<String, String> options = new HashMap<String, String>();

    /**
     * Create a configuration with no options defined.
     */
    public Configuration() {
    }


    /**
     * Create a configuration by copying options from another configuration.
     */
    public Configuration(Configuration other) {
        merge(other.options);
    }

    /**
     * Create a configuration by copying options from a map.
     */
    public Configuration(Map<String, String> map) {
        merge(map);
    }

    /**
     * Create a configuration by copying options from a property list.
     */
    public Configuration(Properties properties) {
        merge(properties);
    }

    /**
     * Define an option by supplying a value.
     * If the configuration already defines the option
     * the given value replaces the old one.
     */
    public void define(String name, String value) {
        options.put(name, value);
    }

    /**
     * Indicate whether this configuration defines the named option.
     */
    public Boolean defines(String name) {
        return options.containsKey(name);
    }

    /**
     * Merge a set of options from another configuration into this configuration.
     */
    public void merge(Configuration other) {
        merge(other.options);
    }

    /**
     * Merge a set of options from a map into this configuration.
     */
    public void merge(Map<String,String> map) {
        options.putAll(map);
    }

    /**
     * Merge a set of options from a property list into this configuration.
     */
    public void merge(Properties properties) {
        for(String propertyName : properties.stringPropertyNames()) {
            options.put(propertyName, properties.getProperty(propertyName));
        }
    }

    /**
     * Merge this configuration's options into another configuration.
     */
    public void mergeInto(Configuration other) {
        mergeInto(other.options);
    }

    /**
     * Merge this configuration's options into a map.
     */
    public void mergeInto(Map<String, String> map) {
        map.putAll(options);
    }

    /**
     * Merge this configuration's options into a property list.
     */
    public void mergeInto(Properties properties) {
        for(Map.Entry<String,String> entry : options.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Return the names of the configuration's defined options.
     */
    public Set<String> names() {
        return Collections.unmodifiableSet(options.keySet());
    }

    /**
     * Return the value of an option.
     * @param name the name of an option
     * @return the value of the option, or {@code null} if the configuration does not define the option
     */
    public String option(String name) {
        return options.get(name);
    }

    /**
     * Return the value of an option,
     * or the given default value if the configuration does not define the option.
     * This method does <em>not</em> add the default value to the configuration.
     * If the option is undefined when this method is called,
     * it remains undefined when the method returns.
     * @param name the name of an option
     * @param defaultValue the value to return if the configuration does not define the option
     * @return the value of the option, or {@code defaultValue} if the configuration does not define the option
     */
    public String option(String name, String defaultValue) {
        return defines(name) ? option(name) : defaultValue;
    }

    /**
     * Return the value of an option,
     * or the value obtained from a supplier if the configuration does not define the option.
     * This method does <em>not</em> add the supplied value to the configuration.
     * If the option is undefined when this method is called,
     * it remains undefined when the method returns.
     * @param name the name of an option
     * @param supplier a supplier that supplies a value if the configuration does not define the option
     * @return the value of the option, or the value supplied by {@code supplier} if the configuration does not define the option
     */
    public String option(String name, Supplier<String> supplier) {
        return defines(name) ? option(name) : supplier.get();
    }

    /**
     * Return the value of a required option.
     * @param name the name of an option
     * @return the value of the option
     * @throws ConfigurationException if this configuration does not define the option
     */
    public String requiredOption(String name) {
        if(defines(name)) return option(name);
        throw new ConfigurationException("The configuration does not define the required option " + name);
    }
}
