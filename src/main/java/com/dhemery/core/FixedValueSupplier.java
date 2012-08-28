package com.dhemery.core;

public class FixedValueSupplier<T> implements Supplier<T> {
    private final T value;

    public FixedValueSupplier(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }
}