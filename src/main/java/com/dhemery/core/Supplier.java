package com.dhemery.core;

/**
 * Supplies a value of type {@code T}.
 * It is up to the implementation
 * whether to supply a newly constructed instance, an existing instance, or {@code null}.
 * @param <T> type of object to supply
 */
public interface Supplier<T> {
    /**
     * Supply a value of type {@code T}.
     */
    T get();
}
