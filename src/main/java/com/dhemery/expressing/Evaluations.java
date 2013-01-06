package com.dhemery.expressing;

import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.LazyStream;
import com.dhemery.core.Sampler;
import org.hamcrest.*;

import java.util.Arrays;

import static com.dhemery.core.SamplerCondition.sampleOf;
import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * Builds fluent expressions
 * to make assertions
 * and evaluate boolean conditions.
 * In contrast to the methods in other {@code Expressive}–related classes,
 * these methods evaluate expressions immediately,
 * without polling.
 * These methods are all declared static,
 * so they can be used without instantiating an {@code Expressive}.
 */
public class Evaluations {

    /**
     * Assert that the condition is satisfied.
     * @param condition the condition to evaluate
     * @throws AssertionError if the condition is not satisfied
     */
    public static void assertThat(Condition condition) {
        assertThat("", condition);
    }

    /**
     * Assert that the condition is satisfied.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param condition the condition to evaluate
     * @throws AssertionError if the condition is not satisfied
     */
    public static void assertThat(String context, Condition condition) {
        if(!condition.isSatisfied()) {
            StringBuilder description = new StringBuilder()
                    .append(context)
                    .append("\nExpected: ")
                    .append(condition)
                    .append("\n     but: ")
                    .append(condition.explainDissatisfaction());
            throw new AssertionError(description.toString());
        }
    }

    /**
     * Assert that a sample of the variable satisfies the criteria.
     * @param variable the variable to evaluate
     * @param criteria the criteria that the sampled variable is to satisfy
     * @throws AssertionError if the variable does not satisfy the criteria
     */
    public static <V> void assertThat(Sampler<V> variable, Matcher<? super V> criteria) {
        assertThat("", variable, criteria);
    }

    /**
     * Assert that a sample of the variable satisfies the criteria.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param variable the variable to evaluate
     * @param criteria the criteria that the sampled variable is to satisfy
     * @throws AssertionError if the variable does not satisfy the criteria
     */
    public static <V> void assertThat(String context, Sampler<V> variable, Matcher<? super V> criteria) {
        assertThat(context, sampleOf(variable, criteria));
    }

    /**
     * Assert that a sample of the boolean variable is true.
     * @param variable the boolean variable to evaluate
     * @throws AssertionError if the variable is false
     */
    public static void assertThat(Sampler<Boolean> variable) {
        assertThat("", variable);
    }

    /**
     * Assert that a sample of the boolean variable is true.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param variable the boolean variable to evaluate
     * @throws AssertionError if the variable is false
     */
    public static void assertThat(String context, Sampler<Boolean> variable) {
        assertThat(context, variable, isQuietlyTrue());
    }

    /**
     * Assert that the feature of the subject satisfies the criteria.
     * @param subject the subject whose feature to evaluate
     * @param feature the feature of the subject to evaluate
     * @param criteria the criteria the feature is to satisfy
     * @throws AssertionError if the feature does not satisfy the criteria
     */
    public static <S,F> void assertThat(S subject, Feature<? super S,F> feature, Matcher<? super F> criteria) {
        assertThat("", subject, feature, criteria);
    }

    /**
     * Assert that the feature of the subject satisfies the criteria.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param subject the subject whose feature to evaluate
     * @param feature the feature of the subject to evaluate
     * @param criteria the criteria the feature is to satisfy
     * @throws AssertionError if the feature does not satisfy the criteria
     */
    public static <S,F> void assertThat(String context, S subject, Feature<? super S,F> feature, Matcher<? super F> criteria) {
        F featureValue = feature.of(subject);
        if(!criteria.matches(featureValue)) {
            StringBuilder description = new StringBuilder()
                    .append(context)
                    .append("\nExamined: ")
                    .append(feature)
                    .append(" of ")
                    .append(subject)
                    .append("\nExpected: ")
                    .append(criteria)
                    .append("\n     but: ")
                    .append(descriptionOfMismatch(featureValue, criteria));
            throw new AssertionError(description.toString());
        }
    }

    /**
     * Assert that the boolean feature of the subject is {@code true}.
     * @param subject the subject whose feature to evaluate
     * @param feature the boolean feature to evaluate
     * @throws AssertionError if the feature is false
     */
    public static <S> void assertThat(S subject, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, isQuietlyTrue());
    }

    /**
     * Assert that the boolean feature of the subject is {@code true}.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param subject the subject whose feature to evaluate
     * @param feature the boolean feature to evaluate
     * @throws AssertionError if the feature is false
     */
    public static <S> void assertThat(String context, S subject, Feature<? super S,Boolean> feature) {
        assertThat(context, subject, feature, isQuietlyTrue());
    }

    /**
     * Assert that the boolean value is true.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param value the boolean value to evaluate
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(String, boolean)}.
     * @throws AssertionError if the boolean value is false
     */
    public static void assertThat(String context, boolean value) {
        MatcherAssert.assertThat(context, value);
    }

    /**
     * Assert that the value satisfies the criteria.
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(Object, org.hamcrest.Matcher)}.
     * @param value the value to evaluate
     * @param criteria the criteria the value is to satisfy
     * @throws AssertionError if the value does not satisfy the criteria
     */
    public static <T> void assertThat(T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(value, criteria);
    }

    /**
     * Assert that the value satisfies the criteria.
     * Delegates to {@link org.hamcrest.MatcherAssert#assertThat(String, Object, org.hamcrest.Matcher)}.
     * @param context the context of this assertion, for inclusion in any resulting {@code AssertionError}
     * @param value the value to evaluate
     * @param criteria the criteria the value is to satisfy
     * @throws AssertionError if the value does not satisfy the criteria
     */
    public static <T> void assertThat(String context, T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(context, value, criteria);
    }

    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return FeatureExpressions.is(feature);
    }

    /**
     * Create an implicit "equalTo" matcher, decorated to make it more expressive.
     * Delegates to {@link org.hamcrest.Matchers#is(Object)}.
     */
    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    /**
     * Decorate a matcher to make it more expressive.
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
     * Create an implicit "equalTo" matcher and decorate it to yield its logical negation.
     * Delegates to {@link org.hamcrest.Matchers#not(Object)}.
     */
    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    /**
     * Decorate a matcher to yield its logical negation.
     * Delegates to {@link org.hamcrest.Matchers#not(org.hamcrest.Matcher)}.
     */
    public static <S> Matcher<S> not(Matcher<S> matcher) {
        return Matchers.not(matcher);
    }

    /**
     * Return a stream over the items in the array.
     */
    public static <T> LazyStream<T> streamOf(T[] items) {
        return streamOf(Arrays.asList(items));
    }

    /**
     * Return a stream over the items supplied by the source iterable.
     */
    public static <T> LazyStream<T> streamOf(Iterable<T> source) {
        return new LazyStream<T>(source);
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

    private static <T> String descriptionOfMismatch(T item, Matcher<? super T> matcher) {
        Description description = new StringDescription();
        matcher.describeMismatch(item, description);
        return description.toString();
    }
}
