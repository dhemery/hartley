package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;

import java.util.Set;

/**
 * A set of named configuration options that can be queried and transformed.
 */
public interface Configuration {
    /**
     * Define an option with the given name and value.
     * Any previous definition of the option is lost.
     * @param name the name of the option
     * @param value the value of the option
     */
    void define(String name, String value);

    /**
     * Return the names of the options defined in this configuration.
     */
    Set<String> names();

    /**
     * Return the value of the named option,
     * transformed by the given operators.
     * @param name the name of the option
     * @param operators the operators to apply to the configured value of the option
     * @return the transformed value of the option
     */
    String option(String name, UnaryOperator<String>... operators);

    /**
     * Return the value of the named option,
     * transformed by the given operators
     * and converted to the specified type.
     * @param name the name of the option
     * @param type the type to which to convert the option
     * @param operators the operators to apply to the configured value of the option
     * @return the transformed value of the option, converted to the specified type
     * @throws IllegalArgumentException if the transformed value cannot be converted to the specified type
     */
    <T> T option(String name, Class<T> type, UnaryOperator<String>... operators);
}
