package com.dhemery.expressing;

import com.dhemery.core.Feature;
import org.hamcrest.Description;

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
public class FeatureExpressions {
    private FeatureExpressions(){}
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
}
