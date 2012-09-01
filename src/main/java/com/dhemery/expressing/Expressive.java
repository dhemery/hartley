package com.dhemery.expressing;

import com.dhemery.configuring.ConfigurationException;
import com.dhemery.core.*;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Poller;
import com.dhemery.polling.Pollers;
import com.dhemery.polling.Ticker;
import com.dhemery.publishing.MethodSubscriptionChannel;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public class Expressive {
    private final Supplier<Publisher> publisher;
    private final Supplier<Ticker> ticker;
    private final Supplier<Poller> poller= Lazily.get(pollerSupplier());

    /**
     * Initialize {@code Expressive} to use the given poller and default ticker.
     */
    protected Expressive(Publisher publisher, Ticker defaultTicker) {
        this(new FixedValueSupplier<Publisher>(publisher), new FixedValueSupplier<Ticker>(defaultTicker));
    }

    /**
     * Initialize {@code Expressive} to get its publisher and default ticker from the given suppliers.
     * {@code Expressive} will call the publisher supplier once, before it conducts its first poll.
     * {@code Expressive} will call the ticker supplier once, before it conducts its first poll
     * that uses the default ticker.
     * @param publisherSupplier supplies the publisher for this {@code Expressive} to use
     * @param tickerSupplier supplies the default ticker for this {@code Expressive} to use
     */
    protected Expressive(Supplier<Publisher> publisherSupplier, Supplier<Ticker> tickerSupplier) {
        publisher = Lazily.get(publisherSupplier);
        ticker = Lazily.get(tickerSupplier);
    }

    /**
     * Initialize {@code Expressive} to get its poller and default ticker by calling methods
     * in the derived class.  {@code Expressive} will call {@link #getPublisher()} once, before
     * it conducts its first poll.  {@code Expressive} will call {@link #getTicker()} once,
     * before it conducts its first poll that uses the default ticker.
     * <p>
     * <strong>IMPORTANT:</strong> If you use this constructor,
     * you must override {@link #getPublisher()} and {@link #getTicker()}.
     * </p>
     */
    protected Expressive() {
        publisher = Lazily.get(defaultPublisherSupplier());
        ticker = Lazily.get(defaultTickerSupplier());
    }

    /**
     * If you inizialized this {@code Expressive} using the no-args {@link #Expressive()} constructor,
     * you must override {@code getTicker()} to provide the default ticker for {@code Expressive} to use.
     * @return the default ticker for {@code Expressive} to use
     */
    protected Ticker getTicker() {
        throw new ConfigurationException("Please override getTicker() to provide a default ticker for Expressive to use.");
    }

    /**
     * If you inizialized this {@code Expressive} using the no-args {@link #Expressive()} constructor,
     * you must override {@code getPoller()} to provide the poller for {@code Expressive} to use.
     * @return the poller for {@code Expressive} to use
     */
    protected Publisher getPublisher() {
        return new MethodSubscriptionChannel();
    }

    /**
     * Return the default ticker.
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
    protected Ticker eventually() {
        return ticker.get();
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
        assertThat(variable, QuietlyTrue.isQuietlyTrue());
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
        assertThat(variable, ticker, QuietlyTrue.isQuietlyTrue());
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
        assertThat(subject, feature, QuietlyTrue.isQuietlyTrue());
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
        assertThat(subject, feature, ticker, QuietlyTrue.isQuietlyTrue());
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
        return the(variable, QuietlyTrue.isQuietlyTrue());
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
        return the(variable, ticker, QuietlyTrue.isQuietlyTrue());
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
        return the(subject, feature, QuietlyTrue.isQuietlyTrue());
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
        return the(subject, feature, ticker, QuietlyTrue.isQuietlyTrue());
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
    public void waitUntil(Ticker ticker, Condition condition) {
        poll(ticker, condition);
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
        waitUntil(variable, eventually(), QuietlyTrue.isQuietlyTrue());
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
        waitUntil(variable, ticker, QuietlyTrue.isQuietlyTrue());
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
        waitUntil(subject, feature, eventually(), QuietlyTrue.isQuietlyTrue());
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
        waitUntil(subject, feature, ticker, QuietlyTrue.isQuietlyTrue());
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
        return when(subject, feature, eventually(), QuietlyTrue.isQuietlyTrue());
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
        return when(subject, feature, ticker, QuietlyTrue.isQuietlyTrue());
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
        when(subject, feature, QuietlyTrue.isQuietlyTrue(), action);
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
        when(subject, feature, ticker, QuietlyTrue.isQuietlyTrue(), action);
    }

    /**
     * @see MatcherAssert#assertThat
     */
    public static void assertThat(String reason, boolean assertion) {
        MatcherAssert.assertThat(reason, assertion);
    }

    /**
     * @see MatcherAssert#assertThat
     */
    public static <T> void assertThat(T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(value, criteria);
    }

    /**
     * @see MatcherAssert#assertThat
     */
    public static <T> void assertThat(String reason, T value, Matcher<? super T> criteria) {
        MatcherAssert.assertThat(reason, value, criteria);
    }

    /**
     * @see Matchers#is(Object)
     */
    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    /**
     * @see Matchers#is(Matcher)
     */
    public static <S> Matcher<S> is(Matcher<S> matcher) {
        return Matchers.is(matcher);
    }

    /**
     * Decorate a boolean feature to make it more expressive.
     */
    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return Expressions.is(feature);
    }

    /**
     * @see Matchers#not(Object)
     */
    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    /**
     * @see Matchers#not(Matcher)
     */
    public static <S> Matcher<S> not(Matcher<S> matcher) {
        return Matchers.not(matcher);
    }

    /**
     * Decorate a boolean feature to yield its logical negation.
     */
    public static <S> Feature<S,Boolean> not(Feature<? super S, Boolean> feature) {
        return Expressions.not(feature);
    }

    private Supplier<Publisher> defaultPublisherSupplier() {
        return new Supplier<Publisher>() {
            @Override
            public Publisher get() {
                return getPublisher();
            }
        };
    }

    private Supplier<Ticker> defaultTickerSupplier() {
        return new Supplier<Ticker>() {
            @Override
            public Ticker get() {
                return getTicker();
            }
        };
    }

    private static <S,V> Sampler<V> sampled(S subject, Feature<? super S, V> feature) {
        return Expressions.sampled(subject, feature);
    }

    private static <V> Condition sampleOf(Sampler<V> variable, Matcher<? super V> criteria) {
        return Expressions.sampleOf(variable, criteria);
    }

    private Supplier<Poller> pollerSupplier() {
        return new Supplier<Poller>() {
            @Override
            public Poller get() {
                return Pollers.publishedWith(publisher.get());
            }
        };
    }

    private void poll(Ticker ticker, Condition condition) {
        poller.get().poll(ticker, condition);
    }

}
