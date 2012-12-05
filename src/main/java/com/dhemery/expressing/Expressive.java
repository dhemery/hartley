package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Ticker;
import com.dhemery.polling.TickingPoller;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public abstract class Expressive {
    /**
     * Return a decorator to decorate a condition in preparation for a poll.
     * Before polling each condition,
     * {@code Expressive} calls this method to obtain a decorator,
     * then calls the decorator's {@link Decorator#decorate(Object)} method
     * to decorate the condition.
     * <p>A typical implementation will return the same decorator for each call.</p>
     * <p>A common use for condition decorators is to wrap the condition in a {@link PublishingCondition}
     * that publishes the result of each evaluation during the poll.</p>
     * @return a condition decorator
     */
    public abstract Decorator<Condition> conditionDecorator();

    /**
     * Return a builder that can build a default ticker for use in polling.
     * Whenever {@code Expressive} needs a default ticker,
     * it first calls this method to obtain a builder,
     * then calls the builder's {@link Builder#build()} method to obtain a ticker.
     * {@code Expressive} obtains a default ticker whenever:
     * <ul>
     * <li>A user calls {@link #eventually()} to obtain a default ticker.</li>
     * <li>A user calls a {@code waitUntil()} or {@code when()} method that does not take an explicit ticker parameter.</li>
     * </ul>
     * A typical implementation will return the same builder for each call.
     * <p><strong>IMPORTANT:</strong>
     * The returned builder <strong>must</strong> build a new ticker each time its {@code build()} method is called.
     * </p>
     * @return a ticker builder
     */
    public abstract Builder<Ticker> tickerBuilder();

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
     * Ticker withinTenSeconds = ...;
     * Condition launchIsInProgress = ...;
     * ...
     * assertThat(withinTenSeconds, launchIsInProgress);
     * }
     */
    public void assertThat(Ticker ticker, Condition condition) {
        poll(ticker, condition);
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
    public <V> void assertThat(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        assertThat(ticker, sampleOf(variable, criteria));
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
    public void assertThat(Ticker ticker, Sampler<Boolean> variable) {
        assertThat(variable, ticker, isQuietlyTrue());
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
     * Ticker withinTenSeconds = ...;
     * ...
     * assertThat(searchResultsPage, resultCount(), withinTenSeconds, is(greaterThan(9)));
     * }
     */
    public <S,V> void assertThat(S subject, Feature<? super S,V> feature, Ticker ticker, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), ticker, criteria);
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
    public <S> void assertThat(S subject, Ticker ticker, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Delegates to {@link MatcherAssert#assertThat(String, boolean)}.
     */
    public static void assertThat(String reason, boolean assertion) {
        MatcherAssert.assertThat(reason, assertion);
    }

    /**
     * Delegates to {@link MatcherAssert#assertThat(Object, Matcher)}.
     */
    public static <T> void assertThat(T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(value, criteria);
    }

    /**
     * Delegates to {@link MatcherAssert#assertThat(String, Object, Matcher)}.
     */
    public static <T> void assertThat(String reason, T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(reason, value, criteria);
    }

    /**
     * Create a new default ticker built by calling the ticker builder.
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
    public Ticker eventually() {
        return tickerBuilder().build();
    }

    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return FeatureExpressions.is(feature);
    }

    /**
     * Delegates to {@link Matchers#is(Object)}.
     */
    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    /**
     * Delegates to {@link Matchers#is(Matcher)}.
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
     * Delegates to {@link Matchers#not(Object)}.
     */
    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    /**
     * Delegates to {@link Matchers#not(Matcher)}.
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
     * Report whether the condition is satisfied during a poll.
     */
    public boolean the(Condition condition, Ticker ticker) {
        try {
            poll(ticker, condition);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
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
     * Report whether a polled sample of the variable satisfies the criteria.
     */
    public <V> boolean the(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria), ticker);
    }

    /**
     * Report whether a polled sample of the variable is {@code true}.
     */
    public boolean the(Sampler<Boolean> variable, Ticker ticker) {
        return the(variable, ticker, isQuietlyTrue());
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

    /**
     * Report whether a polled sample of the feature satisfies the criteria.
     */
    public <S,V> boolean the(S subject, Feature<? super S,V> feature, Ticker ticker, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), ticker, criteria);
    }

    /**
     * Report whether a polled sample of the feature is {@code true}.
     */
    public <S> boolean the(S subject, Ticker ticker, Feature<? super S,Boolean> feature) {
        return the(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Wait until the polled condition is satisfied.
     * Uses a default ticker.
     */
    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    /**
     * Wait until the polled condition is satisfied.
     */
    public void waitUntil(Ticker ticker, Condition condition) {
        poll(ticker, condition);
    }

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     * Uses a default ticker.
     */
    public <V> void waitUntil(Sampler<V> variable, Matcher<? super V> criteria) {
        waitUntil(variable, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the variable is {@code true}.
     * Uses a default ticker.
     */
    public void waitUntil(Sampler<Boolean> variable) {
        waitUntil(variable, eventually(), isQuietlyTrue());
    }

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     */
    public <V> void waitUntil(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(ticker, sampleOf(variable, criteria));
    }

    /**
     * Wait until a polled sample of the variable is [@code true).
     */
    public void waitUntil(Sampler<Boolean> variable, Ticker ticker) {
        waitUntil(variable, ticker, isQuietlyTrue());
    }

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> void waitUntil(S subject, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, eventually(), isQuietlyTrue());
    }

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(sampled(subject, feature), ticker, criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     */
    public <S> void waitUntil(S subject, Ticker ticker, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    public <S,V> S when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> S when(S subject, Feature<? super S,Boolean> feature) {
        return when(subject, feature, eventually(), isQuietlyTrue());
    }

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     */
    public <S,V> S when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(subject, feature, ticker, criteria);
        return subject;
    }

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     */
    public <S> S when(S subject, Ticker ticker, Feature<? super S,Boolean> feature) {
        return when(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    public <S,V> void when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> void when(S subject, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, isQuietlyTrue(), action);
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     */
    public <S,V> void when(S subject, Feature<? super S,V> feature, Ticker ticker, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, ticker, criteria);
        action.actOn(subject);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     */
    public <S> void when(S subject, Ticker ticker, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, ticker, isQuietlyTrue(), action);
    }

    private static <S,V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }

    private static <V> Condition sampleOf(Sampler<V> variable, Matcher<? super V> criteria) {
        return new SamplerCondition<V>(variable, criteria);
    }

    private void poll(Ticker ticker, Condition condition) {
        poller(ticker).poll(decorated(condition));
    }

    private Condition decorated(Condition condition) {
        return conditionDecorator().decorate(condition);
    }

    private TickingPoller poller(Ticker ticker) {
        return new TickingPoller(ticker);
    }
}
