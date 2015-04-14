package com.dhemery.expressing;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

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
     * @param feature the feature to decorate
     * @param <T> the type of the input to the feature
     */
    public static <T> Feature<T,Boolean> is(final Feature<? super T, Boolean> feature) {
        return new NamedFeature<T, Boolean>(name("is", feature)) {
            @Override
            public Boolean of(T object) {
                return feature.of(object);
            }
        };
    }

    /**
     * Decorate a boolean feature to yield its logical negation.
     * @param feature the feature to negate
     * @param <T> the type of the input to the feature
     */
    public static <T> Feature<T,Boolean> not(final Feature<? super T, Boolean> feature) {
        return new NamedFeature<T, Boolean>(name("not", feature)) {
            @Override
            public Boolean of(T object) {
                return !feature.of(object);
            }
        };
    }

    private static <T> String name(String prefix, Feature<? super T, Boolean> feature) {
        Description description = new StringDescription();
        description.appendText(prefix)
                .appendText(" ")
                .appendDescriptionOf(feature);
        return null;
    }
}