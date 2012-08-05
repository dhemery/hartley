package com.dhemery.polling;

import com.dhemery.core.*;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static com.dhemery.polling.QuietlyTrue.isQuietlyTrue;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public abstract class Expressive {
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
     * Return the default poller.
     * This method is named to read nicely in expressions.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * View settingsView = ...;
     * Feature<View,Boolean> visible() { ... }
     * assertThat(settingsView, eventually(), is(visible()));
     * }
     * </pre>
     */
    protected Poller eventually() {
        return defaultPoller.get();
    }

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
     * Assert that the condition is satisfied during a poll.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Poller withinTenSeconds = ...;
     * Condition launchIsInProgress = ...;
     * ...
     * assertThat(withinTenSeconds, launchIsInProgress);
     * }
     */
    public static void assertThat(Poller poller, Condition condition) {
        poller.poll(condition);
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
        assertThat(sampleOf(variable, criteria));
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
        assertThat(variable, isQuietlyTrue());
    }

    /**
     * Assert that a polled sample of the variable satisfies the criteria.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Integer> threadCount = ...;
     * ...
     * assertThat(threadCount, eventually(), is(9));
     * }
     */
    public static <V> void assertThat(Sampler<V> variable, Poller poller, Matcher<? super V> criteria) {
        assertThat(poller, sampleOf(variable, criteria));
    }

    /**
     * Assert that a polled sample of the variable is {@code true}.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Boolean> theresAFlyInMySoup = ...;
     * ...
     * assertThat(eventually(), theresAFlyInMySoup);
     * }
     */
    public static void assertThat(Poller poller, Sampler<Boolean> variable) {
        assertThat(variable, poller, isQuietlyTrue());
    }

    /**
     * Assert that a sample of the feature satisfies the criteria.
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
        assertThat(sampled(subject, feature), criteria);
    }

    /**
     * Assert that a sample of the feature is {@code true}.
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
     * Assert that a polled sample of the feature satisfies the criteria.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Page searchResultsPage = ...;
     * Feature<Page,Boolean> resultCount() { ... }
     * Poller withinTenSeconds = ...;
     * ...
     * assertThat(searchResultsPage, resultCount(), withinTenSeconds, is(greaterThan(9)));
     * }
     */
    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), poller, criteria);
    }

    /**
     * Assert that a polled sample of the feature is {@code true}.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Page settingsPage = ...;
     * Feature<Page,Boolean> displayed() { ... }
     * ...
     * assertThat(settingsPage, eventually(), is(displayed()));
     * }
     */
    public static <S> void assertThat(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, poller, isQuietlyTrue());
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
    public static <V> boolean the(Sampler<V> variable, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria));
    }

    /**
     * Report whether a sample of the variable is {@code true}.
     */
    public static boolean the(Sampler<Boolean> variable) {
        return the(variable, isQuietlyTrue());
    }

    /**
     * Report whether a polled sample of the variable satisfies the criteria.
     */
    public static <V> boolean the(Sampler<V> variable, Poller poller, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria), poller);
    }

    /**
     * Report whether a polled sample of the variable is {@code true}.
     */
    public static boolean the(Sampler<Boolean> variable, Poller poller) {
        return the(variable, poller, isQuietlyTrue());
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
        return the(subject, feature, isQuietlyTrue());
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
        return the(subject, feature, poller, isQuietlyTrue());
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
    public <V> void waitUntil(Sampler<V> variable, Matcher<? super V> criteria) {
        waitUntil(variable, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the variable is {@code true}.
     * Uses the default poller.
     */
    public void waitUntil(Sampler<Boolean> variable) {
        waitUntil(variable, isQuietlyTrue());
    }

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     */
    public static <V> void waitUntil(Sampler<V> variable, Poller poller, Matcher<? super V> criteria) {
        waitUntil(poller, sampleOf(variable, criteria));
    }

    /**
     * Wait until a polled sample of the variable is [@code true).
     */
    public static void waitUntil(Sampler<Boolean> variable, Poller poller) {
        waitUntil(variable, poller, isQuietlyTrue());
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
        waitUntil(subject, feature, isQuietlyTrue());
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
        waitUntil(subject, feature, poller, isQuietlyTrue());
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
        return when(subject, feature, isQuietlyTrue());
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
        return when(subject, feature, poller, isQuietlyTrue());
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
        when(subject, feature, isQuietlyTrue(), action);
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
        when(subject, feature, poller, isQuietlyTrue(), action);
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

    private static <S, V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }

    private static <V> Condition sampleOf(Sampler<V> variable, Matcher<? super V> criteria) {
        return new SamplingCondition<V>(variable, criteria);
    }

}
