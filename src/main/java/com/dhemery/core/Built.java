package com.dhemery.core;

public class Built {
    public static <T> Builder<T> instance(final T instance) {
        return new Builder<T>() {
            @Override
            public T build() {
                return instance;
            }
        };
    }

    public static <T> Builder<T> by(final Supplier<T> supplier) {
        return new Builder<T>() {
            @Override
            public T build() {
                return supplier.get();
            }
        };
    }
}
