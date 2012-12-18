package com.dhemery.configuring;

import java.util.Set;

/**
 * A set of named options.
 */
public interface Options {
    /**
     * Return the names of the defined options.
     */
    Set<String> names();

    /**
     * Return the named option.
     * @param name the name of an option
     * @return the named option, or {@code null} if this set does not define the option
     */
    String option(String name);
}
