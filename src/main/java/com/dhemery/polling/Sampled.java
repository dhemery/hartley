package com.dhemery.polling;

import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Samples a variable and stores the sampled value.
 * @param <V> the type of value to sample
 */
public abstract class Sampled<V> implements SelfDescribing {
    private V value;

    /**
     * Obtain the current value of the sampled variable.
     * @return the sampled value
     */
    protected abstract V takeSample();

    /**
     * Take a sample and store the sampled value.
     */
    public final void sample() {
        value = takeSample();
    }

    /**
     * Return the value obtained by the most recent sample.
     */
    public final V value() {
        return value;
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
