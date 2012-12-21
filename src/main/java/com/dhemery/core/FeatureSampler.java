package com.dhemery.core;

import org.hamcrest.StringDescription;

/**
 * A sampler that samples a feature of a subject.
 * @param <S> the type of subject that has the feature
 * @param <F> the type of feature
 */
public class FeatureSampler<S, F> extends NamedSampler<F> {
    private final S subject;
    private final Feature<? super S, F> feature;
    private F sampledValue;

    /**
     * Create a sampler that samples the feature of the subject.
     * @param subject the subject whose feature to sample
     * @param feature the feature to sample
     */
    public FeatureSampler(S subject, Feature<? super S, F> feature) {
        super(nameOf(subject, feature));
        this.subject = subject;
        this.feature = feature;
    }

    private static <S, F> String nameOf(S subject, Feature<? super S, F> feature) {
        return new StringDescription()
                .appendText("sampled ")
                .appendDescriptionOf(feature)
                .appendText(" of ")
                .appendValue(subject)
                .toString();
    }

    @Override
    public void takeSample() {
        sampledValue = feature.of(subject);
    }

    @Override
    public F sampledValue() {
        return sampledValue;
    }

    /**
     * A factory method for creating {@code FeatureSampler}s.
     */
    public static <S,V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }
}
