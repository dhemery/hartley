package com.dhemery.expressing;

import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

/**
 * A sampler that samples a feature of a subject.
 * @param <S> the type of the subject
 * @param <F> the type of feature to sample
 */
public class FeatureSampler<S, F> implements Sampler<F> {
    private final S subject;
    private final Feature<? super S, F> feature;
    private F sampledValue;

    /**
     * Create a sampler that samples the feature of the subject.
     * @param subject the subject whose feature to sample
     * @param feature the feature to sample
     */
    public FeatureSampler(S subject, Feature<? super S, F> feature) {
        this.subject = subject;
        this.feature = feature;
    }

    @Override
    public void takeSample() {
        sampledValue = feature.of(subject);
    }

    @Override
    public F sampledValue() {
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
