package com.dhemery.creating;

/**
 * Lazy suppliers.
 * @see Lazy
 */
public class Lazily {
    private Lazily(){}
    /**
     * Create a lazy supplier that will obtain its value from another supplier.
     * @param supplier the supplier from which to obtain the value
     * @param <T> the type of value to supply
     * @return a lazy supplier that will obtain its value from the given supplier
     */
    public static <T> Lazy<T> get(Supplier<? extends T> supplier) {
        return new SuppliedLazy<T>(supplier);
    }

    /**
     * Create a lazy supplier that will obtain its value from a builder.
     * @param builder the builder from which to obtain the value
     * @param <T> the type of value to supply
     * @return a lazy supplier that will obtain its value from the given builder
     */
    public static <T> Lazy<T> build(final Builder<? extends T> builder) {
        return new SuppliedLazy<T>(new Supplier<T>() {
            @Override
            public T get() {
                return builder.build();
            }
        });
    }
}
