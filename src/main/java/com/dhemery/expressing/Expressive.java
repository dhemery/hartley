package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Ticker;
import com.dhemery.polling.TickingPoller;
import org.hamcrest.Matcher;

import static com.dhemery.core.FeatureSampler.sampled;
import static com.dhemery.core.SamplerCondition.sampleOf;
import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * Builds fluent expressions to
 * make assertions,
 * evaluate boolean expressions,
 * wait for conditions,
 * and take action when preconditions are met.
 */
public abstract class Expressive extends Evaluations {
    /**
     * Create a new default ticker.
     * This method is called by the {@link #eventually()} method
     * to obtain a default ticker for polling.
     * The {@code eventually()} method is called in two circumstances:
     * <ul>
     * <li>When the expression explicitly calls it.</li>
     * <li>When a polling expression does not include a ticker.</li>
     * </ul>
     * <p>
     * Subclasses must implement this method.
     * <p>
     * <strong>IMPORTANT:</strong>
     * This method must create a new ticker each time it is called.
     * @return a newly constructed default ticker.
     */
    public abstract Ticker createDefaultTicker();

    /**
     * Prepare the condition for polling.
     * This implementation simply returns the condition unenhanced.
     * Subclasses may wish to override this method to enhance the condition before polling.
     * A typical use is to wrap the condition in a {@link PublishingCondition}.
     */
    public Condition prepareToPoll(Condition condition) {
        return condition;
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
    public <S,V> void assertThat(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
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
    public <S> void assertThat(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        assertThat(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Return a new default ticker obtained from this {@code Expressive}'s helper.
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
     * Report whether a polled sample of the feature satisfies the criteria.
     */
    public <S,V> boolean the(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), ticker, criteria);
    }

    /**
     * Report whether a polled sample of the feature is {@code true}.
     */
    public <S> boolean the(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
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
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> void waitUntil(S subject, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, eventually(), isQuietlyTrue());
    }

    /**
     * Wait until a polled sample of the feature satisfies the criteria.
     */
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(sampled(subject, feature), ticker, criteria);
    }

    /**
     * Wait until a polled sample of the feature is {@code true}.
     */
    public <S> void waitUntil(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Return the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    public <S,V> S when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    /**
     * Return the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> S when(S subject, Feature<? super S, Boolean> feature) {
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
    public <S> S when(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        return when(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     * Uses a default ticker.
     */
    public <S,V> void when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     * Uses a default ticker.
     */
    public <S> void when(S subject, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, isQuietlyTrue(), action);
    }

    /**
     * Act on the subject when a polled sample of the feature satisfies the criteria.
     */
    public <S,V> void when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, ticker, criteria);
        action.actOn(subject);
    }

    /**
     * Act on the subject when a polled sample of the feature is {@code true}.
     */
    public <S> void when(S subject, Ticker ticker, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, ticker, isQuietlyTrue(), action);
    }

    private void poll(Ticker ticker, Condition condition) {
        new TickingPoller(ticker).poll(prepareToPoll(condition));
    }
}
