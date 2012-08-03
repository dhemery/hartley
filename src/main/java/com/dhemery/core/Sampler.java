package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * Samples a variable and retains the sample.
 * @param <V> the type of value to sample
 */
public interface Sampler<V> extends SelfDescribing {
    /**
     * Sample the variable and retain the sample.
     */
    void takeSample();

    /**
     * Return the most recent sample.
     */
    V sampledValue();
}
