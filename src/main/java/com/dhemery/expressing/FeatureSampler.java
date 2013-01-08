package com.dhemery.expressing;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedSampler;
import com.dhemery.core.Sampler;
import com.dhemery.factory.Factory;

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
        return new StringBuilder()
                .append("sampled ")
                .append(feature)
                .append(" of ")
                .append(Diagnostic.descriptionOf(subject))
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
     * Create a sampler that samples the feature of the subject.
     */
    @Factory
    public static <S,V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }
}
