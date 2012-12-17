package com.dhemery.configuring;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * A set of configuration options.
 */
public interface Configuration {
    /**
     * Define an option by supplying a value.
     * If the configuration already defines the option
     * the given value replaces the old one.
     */
    void define(String name, String value);

    /**
     * Merge a set of options from another configuration into this configuration.
     */
    void merge(Configuration source);

    /**
     * Merge a set of options from a map into this configuration.
     */
    void merge(Map<String, String> map);

    /**
     * Merge a set of options from a property list into this configuration.
     */
    void merge(Properties properties);

    /**
     * Return the filtered value of an option.
     * @param name the name of an option
     * @param filters the filters to apply to the value of the option
     * @return the value of the option filtered through the filteres
     */
    String option(String name, OptionFilter... filters);

    /**
     * Return the value of a required option.
     * @param name the name of an option
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if this configuration does not define the option
     */
    String requiredOption(String name);

    /**
     * Return a map of this configuration's options.
     */
    Map<String,String> asMap();

    /**
     * Return a properties list with this configuration's options.
     */
    Properties asProperties();

    /**
     * Indicate whether this configuration defines the named option.
     */
    boolean defines(String name);

    /**
     * Return the names of the defined options.
     */
    Set<String> names();

    /**
     * Return the value of an option.
     * @param name the name of an option
     * @return the value of the option, or {@code null} if the configuration does not define the option
     */
    String option(String name);
}
