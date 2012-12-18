package com.dhemery.configuring;

import java.util.Set;

/**
 * Stores options as named strings.
 */
public interface Options {
    /**
     * Return the names of the defined options.
     */
    Set<String> names();

    /**
     * Return the named option.
     * @param name the name of an option
     * @return the named option, or {@code null} if the option is not defined
     */
    String option(String name);
}
