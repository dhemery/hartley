package com.dhemery.configuring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A set of options backed by a {@link Map}.
 * Changes to the options are written through to the underlying map.
 * Changes to the underlying map are reflected in queries to this set's options.
 */
public class MapBackedOptions implements ModifiableOptions {
    private final Map<String, String> map;

    /**
     * Create a set of options backed by an empty map.
     */
    public MapBackedOptions() {
        this(new HashMap<String, String>());
    }

    /**
     * Create a set of options backed by the given map.
     */
    public MapBackedOptions(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void define(String name, String value) {
        map.put(name, value);
    }

    @Override
    public Set<String> names() {
        return new HashSet<String>(map.keySet());
    }

    @Override
    public String option(String name) {
        return map.get(name);
    }
}
