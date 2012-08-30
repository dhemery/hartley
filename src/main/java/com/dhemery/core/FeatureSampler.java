package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * A sampler that samples a feature of an object.
 * @param <T> the type of object that has the feature
 * @param <V> the type of feature
 */
public class FeatureSampler<T, V> implements Sampler<V> {
    private final T subject;
    private final Feature<? super T, V> feature;
    private V sampledValue;

    /**
     * Create a sampler that samples the feature of the subject.
     */
    public FeatureSampler(T subject, Feature<? super T, V> feature) {
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

    private SelfDescribing selfDescribing(T subject) {
        if(subject instanceof SelfDescribing) return (SelfDescribing) subject;
        return new SelfDescribingValue<String>(subject.toString());
    }
}
