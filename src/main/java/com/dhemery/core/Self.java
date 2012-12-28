package com.dhemery.core;

/**
 * A feature that yields its subject.
 * @param <T> the type of subject
 */
public class Self<T> extends NamedFeature<T, T> {
    private static final Feature SELF = new Self();

    public Self() {
        super("self");
    }

    @Override
    public T of(T subject) {
        return subject;
    }

    /**
     * Create a feature that yields its subject.
     */
    public static <T> Feature<T,T> self() { return SELF; }
}
