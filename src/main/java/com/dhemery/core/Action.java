package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * An action to be executed on an object.
 * @param <T> the type of object on which the action can be executed
 */
public interface Action<T> extends SelfDescribing {
    /**
     * Execute the action on an object
     */
    void executeOn(T object);
}
