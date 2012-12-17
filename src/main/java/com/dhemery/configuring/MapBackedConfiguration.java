package com.dhemery.configuring;

import java.util.*;

/**
 * A configuration that uses a map to store and retrieve options.
 * Changes to the configuration are written to the map.
 * Changes to the map are reflected in configuration queries.
 */
public class MapBackedConfiguration extends AbstractConfiguration {
    private final Map<String, String> map;

    /**
     * Create a configuration with no options defined.
     */
    public MapBackedConfiguration() {
        this(new HashMap<String, String>());
    }

    /**
     * Create a configuration that uses the given map to store and retrieve options.
     */
    public MapBackedConfiguration(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void define(String name, String value) {
        map.put(name, value);
    }

    @Override
    public boolean defines(String name) {
        return map.containsKey(name);
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
