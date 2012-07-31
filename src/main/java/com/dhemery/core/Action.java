package com.dhemery.core;

/**
 * An action to be executed on an object.
 * @param <T> the type of object on which the action can be executed
 */
public interface Action<T> {
    /**
     * Execute the action on an object
     */
    void executeOn(T object);
}
