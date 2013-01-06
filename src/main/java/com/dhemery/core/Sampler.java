package com.dhemery.core;

/**
 * Samples a variable and retains the sample.
 * @param <V> the type of variable to sample
 */
public interface Sampler<V> {
    /**
     * Sample the variable and retain the sample.
     */
    void takeSample();

    /**
     * Return the most recent sample.
     */
    V sampledValue();
}
