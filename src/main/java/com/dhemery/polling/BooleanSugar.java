package com.dhemery.polling;

import com.dhemery.core.Feature;
import org.hamcrest.Description;

public class BooleanSugar {
    public static Condition conditionIs(final Condition condition) {
        return new Condition() {
            @Override
            public boolean isSatisfied() {
                return condition.isSatisfied();
            }

            @Override
            public void describeDissatisfactionTo(Description description) {
                condition.describeDissatisfactionTo(description);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is ").appendDescriptionOf(condition);
            }
        };
    }

    public static Condition conditionNot(final Condition condition) {
        return new Condition() {
            @Override
            public boolean isSatisfied() {
                return !condition.isSatisfied();
            }

            @Override
            public void describeDissatisfactionTo(Description description) {
                condition.describeDissatisfactionTo(description);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("not ").appendDescriptionOf(condition);
            }
        };
    }
    /**
     * Wrap a boolean feature to prepend "is " to its description.
     */
    public static <S> Feature<S,Boolean> featureIs(final Feature<? super S, Boolean> feature) {
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
    public static <S> Feature<S,Boolean> featureNot(final Feature<? super S,Boolean> feature) {
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

    public static Sampled<Boolean> sampledIs(final Sampled<Boolean> sampled) {
        return new Sampled<Boolean>() {
            @Override
            protected Boolean takeSample() {
                return sampled.takeSample();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is ").appendDescriptionOf(sampled);
            }
        };
    }

    public static Sampled<Boolean> sampledNot(final Sampled<Boolean> sampled) {
        return new Sampled<Boolean>() {
            @Override
            protected Boolean takeSample() {
                return !sampled.takeSample();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("not ").appendDescriptionOf(sampled);
            }
        };
    }
}
