package com.dhemery.configuring;

import com.dhemery.core.Supplier;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A set of configuration options.
 */
public interface Configuration {
    /**
     * Return a map of this configuration's options.
     */
    Map<String,String> asMap();

    /**
     * Return a properties list with this configuration's options.
     */
    Properties asProperties();

    /**
     * Define an option by supplying a value.
     * If the configuration already defines the option
     * the given value replaces the old one.
     */
    void define(String name, String value);

    /**
     * Indicate whether this configuration defines the named option.
     */
    Boolean defines(String name);

    /**
     * Merge a set of options from another configuration into this configuration.
     */
    void merge(Configuration other);

    /**
     * Merge a set of options from a map into this configuration.
     */
    void merge(Map<String, String> map);

    /**
     * Merge a set of options from a property list into this configuration.
     */
    void merge(Properties properties);

    /**
     * Return the names of the configuration's defined options.
     */
    Set<String> names();

    /**
     * Return the value of an option.
     * @param name the name of an option
     * @return the value of the option, or {@code null} if the configuration does not define the option
     */
    String option(String name);

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
    String option(String name, String defaultValue);

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
    String option(String name, Supplier<String> supplier);

    /**
     * Return the value of a required option.
     * @param name the name of an option
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if this configuration does not define the option
     */
    String requiredOption(String name);
}
