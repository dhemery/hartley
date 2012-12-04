package com.dhemery.core;

/**
 * A supplier that supplies a given fixed object.
 * @param <T> the type of object to supply
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
