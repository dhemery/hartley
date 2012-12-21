package com.dhemery.expressing;

import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import org.hamcrest.*;

import static com.dhemery.core.SamplerCondition.sampleOf;
import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * Composed assertions and evaluations, and syntactic sugar for use with {@link Expressive}.
 * In contrast to the methods in other {@code Expressive}–related classes,
 * these methods evaluate expressions immediately,
 * without polling.
 * These methods are all declared static,
 * so they can be used without instantiating an {@code Expressive}.
 */
public class ImmediateExpressions {
    /**
     * Assert that the condition is satisfied.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Condition lifeIsButADream = ...;
     * ...
     * assertThat(lifeIsButADream);
     * }
     * </pre>
     */
    public static void assertThat(Condition condition) {
        assertThat("", condition);
    }
    public static void assertThat(String reason, Condition condition) {
        if(!condition.isSatisfied()) {
            Description description = new StringDescription();
            description.appendText(reason)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(condition)
                    .appendText("\n     but: ");
            condition.describeDissatisfactionTo(description);
            throw new AssertionError(description.toString());
        }
    }

    /**
     * Assert that a sample of the variable satisfies the criteria.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Integer> threadCount = ...;
     * ...
     * assertThat(threadCount, is(9));
     * }
     */
    public static <V> void assertThat(Sampler<V> variable, Matcher<? super V> criteria) {
        assertThat("", variable, criteria);
    }
    public static <V> void assertThat(String reason, Sampler<V> variable, Matcher<? super V> criteria) {
        assertThat(reason, sampleOf(variable, criteria));
    }

    /**
     * Assert that a sample of the variable is true.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Boolean> theresAFlyInMySoup = ...;
     * ...
     * assertThat(theresAFlyInMySoup);
     * }
     */
    public static void assertThat(Sampler<Boolean> variable) {
        assertThat("", variable);
    }
    public static void assertThat(String reason, Sampler<Boolean> variable) {
        assertThat(reason, variable, isQuietlyTrue());
    }

    /**
     * Assert that the feature of the subject satisfies the criteria.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * TextField userNameField = ...;
     * Feature<TextField,Color> textColor() { ... }
     * Matcher<Color> blue() { ... }
     * ...
     * assertThat(userNameField, textColor(), is(blue()));
     * }
     */
    public static <S,F> void assertThat(S subject, Feature<? super S,F> feature, Matcher<? super F> criteria) {
        assertThat("", subject, feature, criteria);
    }
    public static <S,F> void assertThat(String reason, S subject, Feature<? super S,F> feature, Matcher<? super F> criteria) {
        F featureValue = feature.of(subject);
        if(!criteria.matches(featureValue)) {
            Description description = new StringDescription();
            description.appendText(reason)
                    .appendText("\nExamined: ")
                    .appendDescriptionOf(feature)
                    .appendText(" of ")
                    .appendValue(subject)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(criteria)
                    .appendText("\n     but: ");
            criteria.describeMismatch(featureValue, description);
            throw new AssertionError(description.toString());
        }
    }

    /**
     * Assert that the boolean feature of the subject is {@code true}.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Page settingsPage = ...;
     * Feature<Page,Boolean> displayed() { ... }
     * ...
     * assertThat(settingsPage, is(displayed()));
     * }
     */
    public static <S> void assertThat(S subject, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, isQuietlyTrue());
    }
    public static <S> void assertThat(String reason, S subject, Feature<? super S,Boolean> feature) {
        assertThat(reason, subject, feature, isQuietlyTrue());
    }

    /**
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(String, boolean)}.
     */
    public static void assertThat(String reason, boolean assertion) {
        MatcherAssert.assertThat(reason, assertion);
    }

    /**
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(Object, org.hamcrest.Matcher)}.
     */
    public static <T> void assertThat(T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(value, criteria);
    }

    /**
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(String, Object, org.hamcrest.Matcher)}.
     */
    public static <T> void assertThat(String reason, T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(reason, value, criteria);
    }

    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return FeatureExpressions.is(feature);
    }

    /**
     * Delegates to {@link org.hamcrest.Matchers#is(Object)}.
     */
    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    /**
     * Delegates to {@link org.hamcrest.Matchers#is(org.hamcrest.Matcher)}.
     */
    public static <S> Matcher<S> is(Matcher<S> matcher) {
        return Matchers.is(matcher);
    }

    /**
     * Decorate a boolean feature to yield its logical negation.
     */
    public static <S> Feature<S,Boolean> not(Feature<? super S, Boolean> feature) {
        return FeatureExpressions.not(feature);
    }

    /**
     * Delegates to {@link org.hamcrest.Matchers#not(Object)}.
     */
    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    /**
     * Delegates to {@link org.hamcrest.Matchers#not(org.hamcrest.Matcher)}.
     */
    public static <S> Matcher<S> not(Matcher<S> matcher) {
        return Matchers.not(matcher);
    }

    /**
     * Report whether the condition is satisfied.
     */
    public static boolean the(Condition condition) {
        return condition.isSatisfied();
    }

    /**
     * Report whether a sample of the variable satisfies the criteria.
     */
    public static <V> boolean the(Sampler<V> variable, Matcher<? super V> criteria) {
        variable.takeSample();
        return criteria.matches(variable.sampledValue());
    }

    /**
     * Report whether a sample of the variable is {@code true}.
     */
    public static boolean the(Sampler<Boolean> variable) {
        return the(variable, isQuietlyTrue());
    }

    /**
     * Report whether the subject satisfies the criteria.
     */
    public static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    /**
     * Report whether a sample of the feature satisfies the criteria.
     */
    public static <S,V> boolean the(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return criteria.matches(feature.of(subject));
    }

    /**
     * Report whether a sample of the feature is {@code true}.
     */
    public static <S> boolean the(S subject, Feature<? super S,Boolean> feature) {
        return the(subject, feature, isQuietlyTrue());
    }
}
