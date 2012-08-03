package com.dhemery.polling;

import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * Samples a feature of an object and retains the sampled value;
 * @param <S> the type of object that has the feature
 * @param <V> the type of feature
 */
public class FeatureSampler<S, V> implements Sampler<V> {
    private final S object;
    private final Feature<? super S, V> feature;
    private V sampledValue;

    /**
     * Create a sampler that samples the feature of the object.
     */
    public FeatureSampler(S object, Feature<? super S, V> feature) {
        this.object = object;
        this.feature = feature;
    }

    /**
     * Sample the feature of the object.
     */
    @Override
    public void takeSample() {
        sampledValue = feature.of(object);
    }

    @Override
    public V sampledValue() {
        return sampledValue;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(selfDescribing(object))
                .appendText(" ")
                .appendDescriptionOf(feature);
    }

    private SelfDescribing selfDescribing(S object) {
        if(object instanceof SelfDescribing) return (SelfDescribing) object;
        return new SelfDescribingValue<S>(object);
    }
}
