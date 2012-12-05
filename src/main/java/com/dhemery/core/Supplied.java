package com.dhemery.core;

public class Supplied {
    public static <T> Supplier<T> instance(final T instance) {
        return new Supplier<T>() {
            @Override
            public T get() {
                return instance;
            }
        };
    }

    public static <T> Supplier<T> by(final Builder<T> builder) {
        return new Supplier<T>() {
            @Override
            public T get() {
                return builder.build();
            }
        };
    }
}
