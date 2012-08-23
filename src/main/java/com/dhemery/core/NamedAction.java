package com.dhemery.core;

/**
 * An action that describes itself with a fixed name.
 */
public abstract class NamedAction<T> extends Named implements Action<T> {
    /**
     * Create a named action.
     */
    protected NamedAction(String name) {
        super(name);
    }
}
