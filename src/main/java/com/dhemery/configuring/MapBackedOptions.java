package com.dhemery.configuring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapBackedOptions implements ModifiableOptions {
    private final Map<String, String> map;

    public MapBackedOptions() {
        this(new HashMap<String, String>());
    }

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
