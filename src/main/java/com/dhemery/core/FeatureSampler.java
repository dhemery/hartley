package com.dhemery.core;

import org.hamcrest.Description;

public class FeatureSampler<S, V> implements Sampler<V> {
    public FeatureSampler(S subject, Feature<? super S, V> feature) {


    }

    @Override
    public void takeSample() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public V sampledValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void describeTo(Description description) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
