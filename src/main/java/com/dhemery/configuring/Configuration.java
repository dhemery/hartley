package com.dhemery.configuring;

/**
 * A set of configuration options that can be queried, merged, and transformed.
 */
public interface Configuration extends ModifiableOptions {
    /**
     * Indicate whether named option is defined.
     */
    boolean defines(String name);

    /**
     * Return the named option filtered by the given filters.
     * @param name the name of the options
     * @param filters the filters to transform
     * @return the filtered value of the option
     */
    String option(String name, OptionFilter... filters);

    /**
     * Return the named option, and throw an exception if it is not defined.
     * @param name the name of an option
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if the named option is not defined
     */
    String requiredOption(String name);
}
