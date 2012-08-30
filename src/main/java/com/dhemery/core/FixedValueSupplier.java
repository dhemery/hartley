package com.dhemery.core;

/**
 * A supplier that supplies a given fixed value.
 * @param <T> the type of value supplied
 */
public class FixedValueSupplier<T> implements Supplier<T> {
    private final T value;

    /**
     * Create a supplier that supplies the given value.
     */
    public FixedValueSupplier(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }
}
