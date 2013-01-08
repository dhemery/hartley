package com.dhemery.configuring;

import com.dhemery.core.Feature;
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
     * transformed by the operators.
     * @param name the name of the option
     * @param operators the operators to apply to the configured value of the option
     * @return the transformed value of the option
     */
    String option(String name, UnaryOperator<String>... operators);

    /**
     * Return the value of the named option,
     * transformed by the operators
     * and translated by the translator.
     * @param name the name of the option
     * @param operators the operators to apply to the configured value of the option
     * @param <T> the type to which to translate the transformed option
     * @param translator the translator to translate the option to the desired type
     * @return the transformed value of the option, translated by the specified translator
     */
    <T> T option(Feature<String,T> translator, String name, UnaryOperator<String>... operators);
}
