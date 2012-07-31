package com.dhemery.core;

/**
 * Builds an object of type {@code T}.
 * A builder typically provides methods by which
 * callers can supply configuration parameters
 * for the object to be built.
 * When a caller calls {@link #build()},
 * the builder creates a new object based on the
 * accumulated configuration parameters.
 * @param <T> the type of object to build
 */
public interface Builder<T> {
    /**
     * Build a new object of type {@code T}.
     */
    T build();
}
