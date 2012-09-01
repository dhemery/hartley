package com.dhemery.expressing;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Syntactic sugar for boolean features.
 * <p>
 * Example: If you have a feature (for Cheshire cats) called {@code grinning()},
 * you can write {@link Expressive} phrases like:
 * </p>
 * <pre>
 * {@code
 *
 * assertThat(cheshireCat, is(grinning()));
 * waitUntil(cheshireCat, is(not(grinning)));
 * }
 * </pre>
 */
public class Expressions {
    private Expressions(){}
    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S,Boolean> is(final Feature<? super S, Boolean> feature) {
        return new Feature<S, Boolean>() {
            @Override
            public Boolean of(S object) {
                return feature.of(object);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is ").appendDescriptionOf(feature);
            }
        };
    }

    /**
     * Decorate a boolean feature to yield its logical negation.
     * @param feature the feature whose values to negate
     * @param <S> the type of object that has the feature
     */
    public static <S> Feature<S,Boolean> not(final Feature<? super S, Boolean> feature) {
        return new Feature<S, Boolean>() {
            @Override
            public Boolean of(S object) {
                return !feature.of(object);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("not ").appendDescriptionOf(feature);
            }
        };
    }

    /**
     * Create a sampler that samples a feature of a subject.
     * @param subject the subject that has the feature
     * @param feature the feature to sample
     * @param <S> the type of subject
     * @param <V> the type of feature
     * @return a sampler that samples the feature of the subject.
     */
    public static <S,V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }

    /**
     * Create a condition that is satisfied when a sampled variable matches the criteria.
     * @param variable the variable to sample
     * @param criteria the criteria to satisfy
     * @param <V> the type of sampled variable
     * @return a condition that is satisfied when the sampled variable matches the criteria
     */
    public static <V> Condition sampleOf(Sampler<V> variable, Matcher<? super V> criteria) {
        return new SamplerCondition<V>(variable, criteria);
    }
}