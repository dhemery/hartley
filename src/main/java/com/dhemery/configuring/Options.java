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
    String option(String name);
}
