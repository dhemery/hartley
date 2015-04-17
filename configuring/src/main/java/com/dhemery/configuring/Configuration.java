package com.dhemery.configuring;

import java.util.*;

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

    default Optional<String> optional(String name) {
        return Optional.ofNullable(option(name));
    }

    /**
     * Return the value of a required option.
     * @param name the name of an option
     * @return the value of the option
     * @throws NullPointerException if this configuration does not define the option
     */
    default String requiredOption(String name) {
        return Objects.requireNonNull(option(name), () -> "The configuration does not define the required option " + name);
    }
}
