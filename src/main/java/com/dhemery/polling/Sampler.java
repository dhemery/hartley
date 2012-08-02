package com.dhemery.polling;

import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public abstract class Sampler<V> implements SelfDescribing {
    private V sampledValue;

    protected abstract V takeSample();

    public final void sample() {
        sampledValue = takeSample();
    }

    public final V sampledValue() {
        return sampledValue;
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
