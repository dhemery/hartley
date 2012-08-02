package com.dhemery.core;

/**
 * A feature that describes itself by a fixed name.
 */
public abstract class NamedFeature<T,V> extends Named implements Feature<T,V> {
    /**
     * Create a feature with a fixed name.
     */
    protected NamedFeature(String name) {
        super(name);
    }
}
