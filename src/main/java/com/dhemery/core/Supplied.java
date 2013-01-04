package com.dhemery.core;

/**
 * Static utility methods to create suppliers.
 */
public class Supplied {
    /**
     * Create a supplier that supplies a fixed instance.
     */
    public static <T> Supplier<T> instance(final T instance) {
        return new Supplier<T>() {
            @Override
            public T get() {
                return instance;
            }
        };
    }

    /**
     * Create a supplier that gets instances from a builder.
     */
    public static <T> Supplier<T> by(final Builder<T> builder) {
        return new Supplier<T>() {
            @Override
            public T get() {
                return builder.build();
            }
        };
    }
}
