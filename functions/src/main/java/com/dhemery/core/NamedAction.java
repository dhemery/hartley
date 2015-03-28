package com.dhemery.core;

/**
 * An action that describes itself with a fixed name.
 * @param <S> the type of subject on which to act
 */
public abstract class NamedAction<S> extends Named implements Action<S> {
    /**
     * Create a named action.
     */
    protected NamedAction(String name) {
        super(name);
    }
}
