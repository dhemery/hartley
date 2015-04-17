package com.dhemery.expressions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;

import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.dhemery.expressions.Descriptive.condition;

/**
 * Composed assertions and evaluations, and syntactic sugar for use in expressions.
 * These methods evaluate expressions immediately.
 */
public interface Expressions {
    static void assertThat(Condition condition) {
        if(condition.isSatisfied()) return;
        StringJoiner failedExpectation = new StringJoiner(" ")
                .add("Expected:")
                .add(String.valueOf(condition));
        condition.diagnosis().ifPresent(d -> failedExpectation.add("\n     but:").add(d));
        throw new AssertionError(failedExpectation);
    }

    /**
     * Assert that subject satisfies the predicate.
     * @throws AssertionError with a diagnostic description if the assertion fails
     */
    static <T> void assertThat(T subject, Predicate<? super T> predicate) {
        if(predicate.test(subject)) return;
        StringJoiner failedExpectation = new StringJoiner(" ")
                .add("Expected:")
                .add(String.valueOf(subject))
                .add(String.valueOf(predicate));
        throw new AssertionError(failedExpectation.toString());
    }

    /**
     * Assert that the characteristic that the function extracts from the subject satisfies the matcher.
     * @throws AssertionError with a diagnostic description if the assertion fails
     */
    static <T, R> void assertThat(T subject, Function<? super T, ? extends R> function, Matcher<? super R> matcher) {
        R characteristic = function.apply(subject);
        if(matcher.matches(subject)) return;
        Description mismatchDescription = new StringDescription();
        matcher.describeMismatch(characteristic, mismatchDescription);
        StringJoiner failedExpectation = new StringJoiner(" ")
                .add("Expected:")
                .add(String.valueOf(subject))
                .add(String.valueOf(function))
                .add(String.valueOf(matcher))
                .add("\n     but:")
                .add(String.valueOf(mismatchDescription));
        throw new AssertionError(failedExpectation.toString());
    }

    /**
     * Assert that the expression is true.
     * This method simply calls {@link MatcherAssert#assertThat(String, boolean)}.
     * @throws AssertionError with the given message if the assertion fails
     */
    static void assertThat(String message, boolean expression) {
        MatcherAssert.assertThat(message, expression);
    }

    /**
     * Assert that the value satisfies the matcher.
     * This method simply calls {@link MatcherAssert#assertThat(Object, Matcher)}.
     * @throws AssertionError with a diagnostic description if the assertion fails
     */
    static <T> void assertThat(T value, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(value, matcher);
    }

    /**
     * Assert that the value satisfies the matcher.
     * This method simply calls {@link MatcherAssert#assertThat(String, Object, Matcher)}.
     * @throws AssertionError with the given message if the assertion fails
     */
    static <T> void assertThat(String message, T value, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(message, value, matcher);
    }

    /**
     * Report whether the condition is satisfied.
     */
    static boolean satisfiedThat(Condition condition) {
        return condition.isSatisfied();
    }

    /**
     * Report whether the subject satisfies the matcher.
     */
    static <T> boolean satisfiedThat(T subject, Matcher<? super T> matcher) {
        return matcher.matches(subject);
    }

    /**
     * Report whether the the subject satisfies the predicate.
     */
    static <T> boolean satisfiedThat(T subject, Predicate<? super T> predicate) {
        return predicate.test(subject);
    }

    /**
     * Report whether the characteristic that the function extracts from the subject satisfies the matcher.
     */
    static <T, R> boolean satisfiedThat(T subject, Function<? super T, ? extends R> function, Matcher<? super R> matcher) {
        return matcher.matches(function.apply(subject));
    }
}
