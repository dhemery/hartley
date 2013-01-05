package com.dhemery.core;

import java.util.Set;

/**
 * A set of defined terms.
 */
public interface StringDictionary {
    /**
     * Define a term.
     */
    void define(String term, String definition);

    /**
     * Return the definition of a term.
     */
    String definitionOf(String term);

    /**
     * Return the set of defined terms.
     */
    Set<String> terms();
}
