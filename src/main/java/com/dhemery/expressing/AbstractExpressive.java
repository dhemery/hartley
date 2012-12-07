package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.Sampler;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Ticker;
import org.hamcrest.Matcher;

import static com.dhemery.core.FeatureSampler.sampled;
import static com.dhemery.core.SamplerCondition.sampleOf;
import static com.dhemery.expressing.QuietlyTrue.isQuietlyTrue;

/**
 * An abstract {@link Expressive} that delegates to a subclass to obtain default tickers and conduct polls.
 */
public abstract class AbstractExpressive extends ImmediateExpressions implements Expressive {

    @Override
    public void assertThat(Ticker ticker, Condition condition) {
        poll(ticker, condition);
    }

    @Override
    public <V> void assertThat(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        assertThat(ticker, sampleOf(variable, criteria));
    }

    @Override
    public void assertThat(Ticker ticker, Sampler<Boolean> variable) {
        assertThat(variable, ticker, isQuietlyTrue());
    }

    @Override
    public <S,V> void assertThat(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), ticker, criteria);
    }

    @Override
    public <S> void assertThat(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        assertThat(subject, feature, ticker, isQuietlyTrue());
    }

    /**
     * Return {@code this}.
     * Unless a subclass overrides this behavior, an {@code AbstractExpressive} is its own base.
     */
    @Override
    public Expressive base() {
        return this;
    }

    @Override
    public boolean the(Condition condition, Ticker ticker) {
        try {
            poll(ticker, condition);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    @Override
    public <V> boolean the(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        return the(sampleOf(variable, criteria), ticker);
    }

    @Override
    public boolean the(Sampler<Boolean> variable, Ticker ticker) {
        return the(variable, ticker, isQuietlyTrue());
    }

    @Override
    public <S,V> boolean the(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), ticker, criteria);
    }

    @Override
    public <S> boolean the(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        return the(subject, feature, ticker, isQuietlyTrue());
    }

    @Override
    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    @Override
    public void waitUntil(Ticker ticker, Condition condition) {
        poll(ticker, condition);
    }

    @Override
    public <V> void waitUntil(Sampler<V> variable, Matcher<? super V> criteria) {
        waitUntil(variable, eventually(), criteria);
    }

    @Override
    public void waitUntil(Sampler<Boolean> variable) {
        waitUntil(variable, eventually(), isQuietlyTrue());
    }

    @Override
    public <V> void waitUntil(Sampler<V> variable, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(ticker, sampleOf(variable, criteria));
    }

    @Override
    public void waitUntil(Sampler<Boolean> variable, Ticker ticker) {
        waitUntil(variable, ticker, isQuietlyTrue());
    }

    @Override
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    @Override
    public <S> void waitUntil(S subject, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, eventually(), isQuietlyTrue());
    }

    @Override
    public <S,V> void waitUntil(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(sampled(subject, feature), ticker, criteria);
    }

    @Override
    public <S> void waitUntil(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        waitUntil(subject, feature, ticker, isQuietlyTrue());
    }

    @Override
    public <S,V> S when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    @Override
    public <S> S when(S subject, Feature<? super S, Boolean> feature) {
        return when(subject, feature, eventually(), isQuietlyTrue());
    }

    @Override
    public <S,V> S when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria) {
        waitUntil(subject, feature, ticker, criteria);
        return subject;
    }

    @Override
    public <S> S when(S subject, Ticker ticker, Feature<? super S, Boolean> feature) {
        return when(subject, feature, ticker, isQuietlyTrue());
    }

    @Override
    public <S,V> void when(S subject, Feature<? super S, V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    @Override
    public <S> void when(S subject, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, isQuietlyTrue(), action);
    }

    @Override
    public <S,V> void when(S subject, Feature<? super S, V> feature, Ticker ticker, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, ticker, criteria);
        action.actOn(subject);
    }

    @Override
    public <S> void when(S subject, Ticker ticker, Feature<? super S, Boolean> feature, Action<? super S> action) {
        when(subject, feature, ticker, isQuietlyTrue(), action);
    }

}
