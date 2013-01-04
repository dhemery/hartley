package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;
import org.hamcrest.SelfDescribing;

/**
 * A set of named, configuration options that can be queried and transformed.
 */
public interface Configuration extends ModifiableOptions, SelfDescribing {
    /**
     * Indicate whether named option is defined.
     */
    boolean defines(String name);

    /**
     * Return the named option transformed by the given operators.
     * @param name the name of the options
     * @param queryOperators the operators to apply
     * @return the transformed value of the option
     */
    String option(String name, UnaryOperator<String>... queryOperators);

    /**
     * Return the named option transformed by the given operators.
     * @param name the name of an option
     * @param queryOperators the queryOperators to apply
     * @return the value of the option
     * @throws com.dhemery.configuring.ConfigurationException if the transformed value is {@code null}.
     */
    String requiredOption(String name, UnaryOperator<String>... queryOperators);

    /**
     * Return the named option transformed by the given operators and converted to the specified type.
     * @param name the name of an option
     * @param type the type to which to convert the option
     * @param operators the operators to apply
     * @return the transformed value of the option, converted to the specified type
     * @throws com.dhemery.configuring.ConfigurationException if the transformed value is {@code null}
     * @throws IllegalArgumentException if the transformed value cannot be converted to the specified type
     */
    <T> T requiredOption(String name, Class<T> type, UnaryOperator<String>... operators);
}
