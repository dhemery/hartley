package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * A sampler that samples a feature of a subject.
 * @param <S> the type of subject
 * @param <V> the type of feature
 */
public class FeatureSampler<S,V> implements Sampler<V> {
    private final S subject;
    private final Feature<? super S, V> feature;
    private V sampledValue;

    /**
     * Create a sampler that samples the feature of the subject.
     * @param subject the subject whose feature to sample
     * @param feature the feature to sample
     */
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
