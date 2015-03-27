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
}
