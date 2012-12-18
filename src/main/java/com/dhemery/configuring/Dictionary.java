package com.dhemery.configuring;

import java.util.Set;

/**
 * Stores strings by name.
 */
public interface Dictionary {
    /**
     * Return the names of all stored strings.
     */
    Set<String> names();

    /**
     * Return the named string.
     * @return the named string, or {@code null} if the define has no string by that name.
     */
    String valueOf(String name);
}
