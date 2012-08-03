package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * An action that acts on an object.
 * @param <T> the type of object to act on
 */
public interface Action<T> extends SelfDescribing {
    /**
     * Act on the object.
     */
    void actOn(T object);
}
