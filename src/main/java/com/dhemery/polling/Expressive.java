package com.dhemery.polling;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public abstract class Expressive {
    private static final Matcher<Boolean> IS_QUIETLY_TRUE = new TypeSafeMatcher<Boolean>() {
        @Override
        protected boolean matchesSafely(Boolean value) {
            return value;
        }

        @Override
        public void describeTo(Description description) {}
    };

    private final Lazy<Poller> defaultPoller = Lazily.get(new Supplier<Poller>() {
        @Override
        public Poller get() {
            return defaultPoller();
        }
    });

    /**
     * Implement this method
     * to deliver the default {@link Poller}
     * for use in expressions.
     * {@code Expressive} will call {@code defaultPoller()}
     * at most one time.
     * @return the default poller.
     */
    protected abstract Poller defaultPoller();

    /**
     * <p>Return the default poller.
     * This method is named to read nicely in expressions:
     * </p>
     * <pre>
     * {@code
     * assertThat(theCheshireCat, eventually(), is(grinning()));
     * }
     * </pre>
     */
    protected Poller eventually() {
        return defaultPoller.get();
    }

    /**
     * Assert that the condition is true.
     */
    public static void assertThat(Condition condition) {
        ConditionAssert.assertThat(condition);
    }

    /**
     * Assert that the condition is satisfied during a poll.
     */
    public static void assertThat(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    /**
     * Assert that a sample of the variable satisfies the criteria.
     */
    public static <V> void assertThat(Sampled<V> variable, Matcher<? super V> criteria) {
        assertThat(sampleOf(variable, criteria));
    }

    /**
     * Assert that a sample of the variable is true.
     */
    public static void assertThat(Sampled<Boolean> variable) {
        assertThat(variable, IS_QUIETLY_TRUE);
    }

    /**
     * Assert that a polled sample of the variable satisfies the criteria.
     */
    public static <V> void assertThat(Sampled<V> variable, Poller poller, Matcher<? super V> criteria) {
        assertThat(poller, sampleOf(variable, criteria));
    }

    /**
     * Assert that a polled sample of the variable is {@code true}.
     */
    public static void assertThat(Poller poller, Sampled<Boolean> variable) {
        assertThat(variable, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Assert that a sample of the feature satisfies the criteria.
     */
    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), criteria);
    }

    /**
     * Assert that a sample of the feature is {@code true}.
     */
    public static <S> void assertThat(S subject, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, IS_QUIETLY_TRUE);
    }

    /**
     * Assert that a polled sample of the feature satisfies the criteria.
     */
    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), poller, criteria);
    }

    /**
     * Assert that a polled sample of the feature is {@code true}.
     */
    public static <S> void assertThat(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Report whether the condition is satisfied.
     */
    public static boolean the(Condition condition) {
        return condition.isSatisfied();
    }

    /**
     * Report whether the condition is satisfied during a poll.
     */
    public static boolean the(Condition condition, Poller poller) {
        try {
            poller.poll(condition);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Report whether a sample of the variable satisfies the criteria.
     */
    public static <V> boolean the(Sampled<V> variable, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria));
    }

    /**
     * Report whether a sample of the variable is {@code true}.
     */
    public static boolean the(Sampled<Boolean> variable) {
        return the(variable, IS_QUIETLY_TRUE);
    }

    /**
     * Report whether a polled sample of the variable satisfies the criteria.
     */
    public static <V> boolean the(Sampled<V> variable, Poller poller, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria), poller);
    }

    /**
     * Report whether a polled sample of the variable is {@code true}.
     */
    public static boolean the(Sampled<Boolean> variable, Poller poller) {
        return the(variable, poller, IS_QUIETLY_TRUE);
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
        return the(sampled(subject, feature), criteria);
    }

    /**
     * Report whether a sample of the feature is {@code true}.
     */
    public static <S> boolean the(S subject, Feature<? super S,Boolean> feature) {
        return the(subject, feature, IS_QUIETLY_TRUE);
    }

    /**
     * Report whether a polled sample of the feature satisfies the criteria.
     */
    public static <S,V> boolean the(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), poller, criteria);
    }

    /**
     * Report whether a polled sample of the feature is {@code true}.
     */
    public static <S> boolean the(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        return the(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Wait until the polled condition is satisfied.
     * Uses the default poller.
     */
    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    /**
     * Wait until the polled condition is satisfied.
     */
    public static void waitUntil(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     * Uses the default poller.
     */
    public <V> void waitUntil(Sampled<V> variable, Matcher<? super V> criteria) {
        waitUntil(variable, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the variable is {@code true}.
     * Uses the default poller.
     */
    public void waitUntil(Sampled<Boolean> variable) {
        waitUntil(variable, IS_QUIETLY_TRUE);
    }

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     */
    public static <V> void waitUntil(Sampled<V> variable, Poller poller, Matcher<? super V> criteria) {
        waitUntil(poller, sampleOf(variable, criteria));
    }

    /**
     * Wait until a polled sample of the variable is [@code true).
     */
    public static void waitUntil(Sampled<Boolean> variable, Poller poller) {
        waitUntil(variable, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     * Uses the default poller.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     * Uses the default poller.
     */
    public <S> void waitUntil(S subject, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, IS_QUIETLY_TRUE);
    }

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     */
    public static <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        waitUntil(sampled(subject, feature), poller, criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     */
    public static <S> void waitUntil(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     * Uses the default poller.
     */
    public <S,V> S when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     * Uses the default poller.
     */
    public <S> S when(S subject, Feature<? super S,Boolean> feature) {
        return when(subject, feature, IS_QUIETLY_TRUE);
    }

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     */
    public static <S,V> S when(S subject, Feature<? super S, V> feature, Poller poller, Matcher<? super V> criteria) {
        waitUntil(subject, feature, poller, criteria);
        return subject;
    }

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     */
    public static <S> S when(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        return when(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     * Uses the default poller.
     */
    public <S,V> void when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     * Uses the default poller.
     */
    public <S> void when(S subject, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, IS_QUIETLY_TRUE, action);
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     */
    public static <S,V> void when(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, poller, criteria);
        action.actOn(subject);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     */
    public static <S> void when(S subject, Poller poller, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, poller, IS_QUIETLY_TRUE, action);
    }

    /**
     * @see Matchers#is(Object)
     */
    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    /**
     * @see Matchers#is(org.hamcrest.Matcher)
     */
    public static <S> Matcher<S> is(Matcher<S> matcher) {
        return Matchers.is(matcher);
    }

    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return Features.is(feature);
    }

    /**
     * @see Matchers#not(Object)
     */
    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    /**
     * @see Matchers#not(org.hamcrest.Matcher)
     */
    public static <S> Matcher<S> not(Matcher<S> matcher) {
        return Matchers.not(matcher);
    }

    /**
     * Decorate a boolean feature to yield its logical negation.
     */
    public static <S> Feature<S,Boolean> not(Feature<? super S, Boolean> feature) {
        return Features.not(feature);
    }

    private static <S, V> Sampled<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }

    private static <V> Condition sampleOf(Sampled<V> variable, Matcher<? super V> criteria) {
        return new SamplingCondition<V>(variable, criteria);
    }
}
