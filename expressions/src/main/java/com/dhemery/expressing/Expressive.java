package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Ticker;
import com.dhemery.polling.TickingPoller;
import org.hamcrest.Matcher;

import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * Composable methods to
 * evaluate boolean expressions,
 * make assertions,
 * wait for conditions,
 * and take action when preconditions are met.
 */
public abstract class Expressive extends ImmediateExpressions {
    public abstract Ticker createDefaultTicker();

    /**
     * Prepare the condition for polling.
     * This implementation simply returns the condition unchanged.
     * Subclasses may wish to override this method to enhance the condition before polling.
     * A typical use is to wrap the condition
     * in a decorator
     * that logs each result.
     */
    public Condition prepareToPoll(Condition condition) {
        return condition;
    }

    /**
     * Assert that the condition is satisfied within the time frame.
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
    public void assertThat(Ticker timeframe, Condition condition) {
        poll(timeframe, condition);
    }

    /**
     * Assert that the sampler's sampled value satisfies the criteria within the time frame.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Integer> threadCount = ...;
     * ...
     * assertThat(threadCount, eventually(), is(9));
     * }
     */
    public <V> void assertThat(Sampler<V> sampler, Ticker timeframe, Matcher<? super V> criteria) {
        assertThat(timeframe, new SamplingCondition<>(sampler, criteria));
    }

    /**
     * Assert that the sampler's sampled value is {@code true} within the time frame.
     * <p>Example:</p>
     * <pre>
     * {@code
     *
     * Sampler<Boolean> theresAFlyInMySoup = ...;
     * ...
     * assertThat(eventually(), theresAFlyInMySoup);
     * }
     */
    public void assertThat(Ticker timeframe, Sampler<Boolean> sampler) {
        assertThat(sampler, timeframe, isQuietlyTrue());
    }

    /**
     * Assert that the feature of the subject satisfies the criteria within the time frame.
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
    public <S,V> void assertThat(S subject, Feature<? super S, V> feature, Ticker timeframe, Matcher<? super V> criteria) {
        assertThat(new FeatureSampler<>(subject, feature), timeframe, criteria);
    }

    /**
     * Assert that the feature of the subject is {@code true} within the time frame.
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
    public <S> void assertThat(S subject, Ticker timeframe, Feature<? super S, Boolean> feature) {
        assertThat(subject, feature, timeframe, isQuietlyTrue());
    }

    /**
     * Return a new default time frame obtained from this {@code Expressive}'s helper.
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
        return createDefaultTicker();
    }

    /**
     * Report whether the condition is satisfied within the time frame.
     */
    public boolean the(Condition condition, Ticker timeframe) {
        try {
            poll(timeframe, condition);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Report whether the sampler's sampled value satisfies the criteria within the time frame.
     */
    public <V> boolean the(Sampler<V> sampler, Ticker timeframe, Matcher<? super V> criteria) {
        return the(new SamplingCondition<>(sampler, criteria), timeframe);
    }

    /**
     * Report whether the sampler's sampled value is {@code true} within the time frame.
     */
    public boolean the(Sampler<Boolean> sampler, Ticker timeframe) {
        return the(sampler, timeframe, isQuietlyTrue());
    }

    /**
     * Report whether the feature of the subject satisfies the criteria within the time frame.
     */
    public <S,V> boolean the(S subject, Feature<? super S, V> feature, Ticker timeframe, Matcher<? super V> criteria) {
        return the(new FeatureSampler<>(subject, feature), timeframe, criteria);
    }

    /**
     * Report whether the boolean feature of the subject is {@code true} within the time frame.
     */
    public <S> boolean the(S subject, Ticker timeframe, Feature<? super S, Boolean> feature) {
        return the(subject, feature, timeframe, isQuietlyTrue());
    }

    /**
     * Wait until the polled condition is satisfied or the default time frame expires.
     */
    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    /**
     * Wait until the polled condition is satisfied or the time frame expires.
     */
    public void waitUntil(Ticker timeframe, Condition condition) {
        poll(timeframe, condition);
    }

    /**
     * Wait until a sampler's sampled value satisfies the criteria or the default time frame expires.
     */
    public <V> void waitUntil(Sampler<V> sampler, Matcher<? super V> criteria) {
        waitUntil(sampler, eventually(), criteria);
    }

    /**
     * Wait until the sampler's sampled value is {@code true} or the default time frame expires.
     */
    public void waitUntil(Sampler<Boolean> sampler) {
        waitUntil(sampler, eventually(), isQuietlyTrue());
    }

    /**
     * Wait until the sampler's sampled value satisfies the criteria or the time frame expires.
     */
    public <V> void waitUntil(Sampler<V> sampler, Ticker timeframe, Matcher<? super V> criteria) {
        waitUntil(timeframe, new SamplingCondition<>(sampler, criteria));
    }

    /**
     * Wait until the sampler's sampled value is [@code true) or the time frame expires.
     */
    public void waitUntil(Sampler<Boolean> sampler, Ticker timeframe) {
        waitUntil(sampler, timeframe, isQuietlyTrue());
    }

    /**
     * Wait until the feature of the subject satisfies the criteria or the default time frame expires.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    /**
     * Wait until the feature of the subject is {@code true} or the default time frame expires.
     */
    public <S> void waitUntil(S subject, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, eventually(), isQuietlyTrue());
    }

    /**
     * Wait until the feature of the subject satisfies the criteria or the time frame expires.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Ticker timeframe, Matcher<? super V> criteria) {
        waitUntil(new FeatureSampler<>(subject, feature), timeframe, criteria);
    }

    /**
     * Wait until the feature of the subject is {@code true} or the time frame expires.
     */
    public <S> void waitUntil(S subject, Ticker timeframe, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, timeframe, isQuietlyTrue());
    }

    /**
     * Return the subject if the feature of the subject satisfies the criteria within the default time frame.
     */
    public <S,V> S when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    /**
     * Return the subject if the feature of the subject is {@code true} within the default time frame.
     */
    public <S> S when(S subject, Feature<? super S, Boolean> feature) {
        return when(subject, feature, eventually(), isQuietlyTrue());
    }

    /**
     * Return the subject if the feature of the subject satisfies the criteria within the time frame.
     */
    public <S,V> S when(S subject, Feature<? super S, V> feature, Ticker timeframe, Matcher<? super V> criteria) {
        waitUntil(subject, feature, timeframe, criteria);
        return subject;
    }

    /**
     * Return the subject if the feature of the subject is {@code true} within the time frame.
     */
    public <S> S when(S subject, Ticker timeframe, Feature<? super S, Boolean> feature) {
        return when(subject, feature, timeframe, isQuietlyTrue());
    }

    /**
     * Perform the action on the subject if the feature of the subject satisfies the criteria within the default time frame.
     */
    public <S,V> void when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    /**
     * Perform the action on the subject if the feature of the subject is {@code true} within the default time frame.
     */
    public <S> void when(S subject, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, isQuietlyTrue(), action);
    }

    /**
     * Perform the action on the subject if the feature of the subject satisfies the criteria within the time frame.
     */
    public <S,V> void when(S subject, Feature<? super S, V> feature, Ticker timeframe, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, timeframe, criteria);
        action.actOn(subject);
    }

    /**
     * Perform the action on the subject if the feature of the subject is {@code true} within the time frame.
     */
    public <S> void when(S subject, Ticker timeframe, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, timeframe, isQuietlyTrue(), action);
    }

    private void poll(Ticker timeframe, Condition condition) {
        new TickingPoller(timeframe).poll(prepareToPoll(condition));
    }
}
