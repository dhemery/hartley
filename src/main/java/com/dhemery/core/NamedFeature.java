package com.dhemery.core;

/**
 * A feature that describes itself with a fixed name.
 * @param <S> the type of subject that has the feature
 * @param <F> the type of feature
 */
public abstract class NamedFeature<S, F> extends Named implements Feature<S, F> {
    /**
     * Create a named feature.
     */
    protected NamedFeature(String name) {
        super(name);
    }
}
