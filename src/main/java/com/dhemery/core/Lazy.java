package com.dhemery.core;

/**
 * A lazy supplier that caches the first value it supplies,
 * and always yields the cached value.
 * @param <T> the type of value to supply
 */
public interface Lazy<T> extends Supplier<T>{
    /**
     * Indicate whether this lazy supplier has supplied a value.
     * This method returns {@code true} if {@link #get()} has been called,
     * and {@code false} otherwise.
     */
    boolean hasAValue();
}
