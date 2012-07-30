package com.dhemery.evaluating;

/**
 * An action to be performed on an object.
 * @param <T> the type of object on which the action can be performed
 */
public interface Action<T> {
    /**
     * Perform the action on an object
     */
    void executeOn(T object);
}
