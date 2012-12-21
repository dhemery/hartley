package com.dhemery.core;

public class SamplerFeature<V> extends NamedFeature<Sampler<V>,V> {
    protected SamplerFeature() {
        super("");
    }

    @Override
    public V of(Sampler<V> subject) {
        subject.takeSample();
        return subject.sampledValue();
    }

    public static <V> Feature<Sampler<V>, V> sample() {
        return new SamplerFeature<V>();
    }
}
