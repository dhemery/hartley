package com.dhemery.core;

import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

public class FeatureSampler<S,V> implements Sampler<V> {
    private final S subject;
    private final Feature<? super S, V> feature;
    private V sampledValue;

    public FeatureSampler(S subject, Feature<? super S, V> feature) {
        this.subject = subject;
        this.feature = feature;
    }

    @Override
    public void takeSample() {
        sampledValue = feature.of(subject);
    }

    @Override
    public V sampledValue() {
        return sampledValue;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(selfDescribing(subject))
                .appendText(" ")
                .appendDescriptionOf(feature);

    }

    private SelfDescribing selfDescribing(S subject) {
        if(subject instanceof SelfDescribing) return (SelfDescribing) subject;
        return new SelfDescribingValue<S>(subject);
    }
}
