package com.dhemery.core;

/**
 * A feature that describes itself with a fixed name.
 */
public abstract class NamedFeature<T,V> extends Named implements Feature<T,V> {
    /**
     * Create a named feature.
     */
    protected NamedFeature(String name) {
        super(name);
    }
}
