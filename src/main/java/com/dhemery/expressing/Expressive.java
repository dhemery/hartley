package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import com.dhemery.polling.Ticker;
import org.hamcrest.Matcher;

/**
 * Expressive methods to make assertions,
 * evaluate boolean expressions,
 * wait for conditions,
 * and perform actions when preconditions are met.
 */
public interface Expressive {
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
    void assertThat(Ticker ticker, Condition condition);

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
    <V> void assertThat(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria);

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
    void assertThat(Ticker ticker, Sampler<Boolean> variable);

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
    <S,V> void assertThat(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria);

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
    <S> void assertThat(S subject, Ticker ticker, Feature<? super S, Boolean> feature);

    /**
     * Return the {@code Expressive} that ultimately
     * supplies this {@code Expressive}'s tickers
     * and performs this {@code Expressive}'s polls.
     * Typically one {@code Expressive} in a system is the base,
     * and all others are {@link ForwardingExpressive}s
     * that delegate to the base to obtain default tickers and perform polls.
     */
    Expressive base();

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
    Ticker eventually();

    /**
     * Poll using the ticker until the condition is satisfied or the ticker expires.
     */
    void poll(Ticker ticker, Condition condition);

    /**
     * Report whether the condition is satisfied during a poll.
     */
    boolean the(Condition condition, Ticker ticker);

    /**
     * Report whether a polled sample of the variable satisfies the criteria.
     */
    <V> boolean the(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria);

    /**
     * Report whether a polled sample of the variable is {@code true}.
     */
    boolean the(Sampler<Boolean> variable, Ticker ticker);

    /**
     * Report whether a polled sample of the feature satisfies the criteria.
     */
    <S,V> boolean the(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria);

    /**
     * Report whether a polled sample of the feature is {@code true}.
     */
    <S> boolean the(S subject, Ticker ticker, Feature<? super S, Boolean> feature);

    /**
     * Wait until the polled condition is satisfied.
     * Uses a default ticker.
     */
    void waitUntil(Condition condition);

    /**
     * Wait until the polled condition is satisfied.
     */
    void waitUntil(Ticker ticker, Condition condition);

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     * Uses a default ticker.
     */
    <V> void waitUntil(Sampler<V> variable, Matcher<? super V> criteria);

    /**
     * Wait until a polled sample of the variable is {@code true}.
     * Uses a default ticker.
     */
    void waitUntil(Sampler<Boolean> variable);

    /**
     * Wait until a polled sample of the variable satisfies the criteria.
     */
    <V> void waitUntil(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria);

    /**
     * Wait until a polled sample of the variable is [@code true).
     */
    void waitUntil(Sampler<Boolean> variable, Ticker ticker);

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria);

    /**
     * Wait until a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    <S> void waitUntil(S subject, Feature<? super S, Boolean> feature);

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     */
    <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria);

    /**
     * Wait until a polled sample of the feature is {@code true}.
     */
    <S> void waitUntil(S subject, Ticker ticker, Feature<? super S, Boolean> feature);

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    <S,V> S when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria);

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    <S> S when(S subject, Feature<? super S, Boolean> feature);

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     */
    <S,V> S when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria);

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     */
    <S> S when(S subject, Ticker ticker, Feature<? super S, Boolean> feature);

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    <S,V> void when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria, Action<? super S> action);

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    <S> void when(S subject, Feature<? super S, Boolean> feature, Action<? super S> action);

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     */
    <S,V> void when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria, Action<? super S> action);

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     */
    <S> void when(S subject, Ticker ticker, Feature<? super S, Boolean> feature, Action<? super S> action);
}
