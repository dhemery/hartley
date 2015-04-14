package com.dhemery.expressing;

import com.dhemery.core.*;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

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
     * Assert that the condition is true.
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
        ConditionAssert.assertThat(condition);
    }

    /**
     * Assert that the sampler's sampled value satisfies the criteria.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Integer> threadCount = ...;
     * ...
     * assertThat(threadCount, is(9));
     * }
     */
    public static <V> void assertThat(Sampler<V> sampler, Matcher<? super V> criteria) {
        assertThat(new SamplingCondition<>(sampler, criteria));
    }

    /**
     * Assert that the sampler's sampled value is {@code true}.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Boolean> theresAFlyInMySoup = ...;
     * ...
     * assertThat(theresAFlyInMySoup);
     * }
     */
    public static void assertThat(Sampler<Boolean> sampler) {
        assertThat(sampler, isQuietlyTrue());
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
    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        assertThat(new FeatureSampler<>(subject, feature), criteria);
    }

    /**
     * Assert that the feature of the subject is {@code true}.
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
     * Report whether the sampler's sampled value satisfies the criteria.
     */
    public static <V> boolean the(Sampler<V> sampler, Matcher<? super V> criteria) {
        sampler.takeSample();
        return criteria.matches(sampler.sampledValue());
    }

    /**
     * Report whether the sampler's sampled boolean value is {@code true}.
     */
    public static boolean the(Sampler<Boolean> sampler) {
        return the(sampler, isQuietlyTrue());
    }

    /**
     * Report whether the subject satisfies the criteria.
     */
    public static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    /**
     * Report whether the feature of the subject satisfies the criteria.
     */
    public static <S,V> boolean the(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return criteria.matches(feature.of(subject));
    }

    /**
     * Report whether the feature of the subject is {@code true}.
     */
    public static <S> boolean the(S subject, Feature<? super S,Boolean> feature) {
        return the(subject, feature, isQuietlyTrue());
    }
}
