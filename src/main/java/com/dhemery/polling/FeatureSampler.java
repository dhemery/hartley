package com.dhemery.polling;

import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * Samples a feature of a object.
 * @param <T> the type of object that has the feature
 * @param <V> the type of feature
 */
public class FeatureSampler<T, V> extends Sampled<V> {
    private final T object;
    private final Feature<? super T, V> feature;

    /**
     * Create a sampler that samples the feature of the object.
     * @param object a object that has the feature
     * @param feature the feature to sample
     */
    public FeatureSampler(T object, Feature<? super T, V> feature) {
        this.object = object;
        this.feature = feature;
    }

    @Override
    protected V takeSample() {
        return feature.of(object);
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(selfDescribing(object)).appendText(" ").appendDescriptionOf(feature);
    }

    private SelfDescribing selfDescribing(T subject) {
        if(subject instanceof SelfDescribing) return (SelfDescribing) subject;
        return new SelfDescribingValue<T>(subject);
    }
}
