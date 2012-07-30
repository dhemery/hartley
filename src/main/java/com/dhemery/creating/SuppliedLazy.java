package com.dhemery.creating;

/**
 * Lazily obtains a value of type {@code T} from another supplier.
 * @param <T> the type of object to build
 * @see Lazy
 */
public class SuppliedLazy<T> implements Lazy<T> {
    private boolean hasAValue;
    private T value;
    private final Supplier<? extends T> supplier;

    /**
     * Create a lazy supplier that obtains its value from another supplier.
     */
    public SuppliedLazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        hasAValue = false;
    }

    @Override
    public T get() {
        if(!hasAValue) {
            value = supplier.get();
            hasAValue = true;
        }
        return value;
    }

    @Override
    public boolean hasAValue() {
        return hasAValue;
    }
}
