package com.dhemery.core;

/**
 * A query that describes itself by a fixed name.
 */
public abstract class NamedQuery<T,V> extends Named implements Query<T,V> {
    /**
     * Create a query with a fixed name.
     */
    protected NamedQuery(String name) {
        super(name);
    }
}
