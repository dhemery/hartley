package com.dhemery.configuring;

import com.dhemery.core.StringDictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A dictionary stored in a {@link Map}.
 * Changes to the dictionary are written through to the underlying map.
 * Entries in the underlying map are reflected in the dictionary.
 */
public class MappedDictionary implements StringDictionary {
    private final Map<String, String> map;

    /**
     * Create an empty dictionary stored in a new map.
     */
    public MappedDictionary() {
        this(new HashMap<String, String>());
    }

    /**
     * Create a dictionary stored in the given map.
     */
    public MappedDictionary(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void define(String term, String definition) {
        map.put(term, definition);
    }

    @Override
    public Set<String> terms() {
        return new HashSet<String>(map.keySet());
    }

    @Override
    public String definitionOf(String term) {
        return map.get(term);
    }
}
