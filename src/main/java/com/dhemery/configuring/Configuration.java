package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;

/**
 * A set of configuration options that can be queried, merged, and transformed.
 */
public interface Configuration extends ModifiableOptions {
    /**
     * Indicate whether named option is defined.
     */
    boolean defines(String name);

    /**
     * Return the named option transformed by the given operators.
     * @param name the name of the options
     * @param operators the operators to apply
     * @return the transformed value of the option
     */
    String option(String name, UnaryOperator<String>... operators);

    /**
     * Return the named option transformed by the given operators, and throw an exception if the value is {@code null}.
     * @param name the name of an option
     * @param operators the operators to apply
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if the value is {@code null}.
     */
    String requiredOption(String name, UnaryOperator<String>... operators);
}
