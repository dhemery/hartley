package com.dhemery.polling;

import com.dhemery.core.Feature;
import org.hamcrest.Description;

public class Features {
    /**
     * Wrap a boolean feature to prepend "is " to its description.
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
     * Wrap a boolean feature to negate its values and prepend "not " to its description.
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
