package com.dhemery.core;

/**
 * An action that describes itself by a fixed name.
 */
public abstract class NamedAction<T> extends Named implements Action<T> {
    /**
     * Create an action with a fixed name.
     */
    protected NamedAction(String name) {
        super(name);
    }
}
