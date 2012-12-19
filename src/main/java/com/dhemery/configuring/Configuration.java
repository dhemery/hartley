package com.dhemery.configuring;

import com.dhemery.core.Feature;

/**
 * A set of configuration options that can be queried, merged, and transformed.
 */
public interface Configuration extends ModifiableOptions {
    /**
     * Indicate whether named option is defined.
     */
    boolean defines(String name);

    /**
     * Return the named option transformed by the given transformations.
     * @param name the name of the options
     * @param transformations the transformations to perform
     * @return the transformed value of the option
     */
    String option(String name, Feature<Option,String>... transformations);

    /**
     * Return the named option, and throw an exception if it is not defined.
     * @param name the name of an option
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if the named option is not defined
     */
    String requiredOption(String name);
}
