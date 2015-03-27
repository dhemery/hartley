package com.dhemery.core;

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
}
