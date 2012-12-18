package com.dhemery.configuring;

import java.util.Map;
import java.util.Properties;

/**
 * A set of configuration options.
 */
public interface Configuration extends ModifiableOptions {
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
     * Return the named option filtered by the given filters.
     * @param name the name of the options
     * @param filters the filters to apply
     * @return the filtered value of the option
     */
    String option(String name, OptionFilter... filters);

    /**
     * Return the named option, and throw an exception if it is not defined.
     * @param name the name of an option
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if this configuration does not define the option
     */
    String requiredOption(String name);
}
