package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.Ticker;

/**
 * An {@link Expressive} that forwards to another {@code Expressive}
 * to obtain default tickers and perform polls.
 */
public class ForwardingExpressive extends AbstractExpressive {
    private final Expressive base;

    /**
     * Create an {@code Expressive}
     * that forwards to the source {@code Expressive}'s base
     * to obtain default tickers and perform polls.
     * Note that the source {@code Expressive} may or may not be its own base.
     */
    public ForwardingExpressive(Expressive source) {
        base = source.base();
    }

    /**
     * Return the base {@code Expressive} that supplies default tickers and performs polls
     * for this {@code ForwardingExpressive}.
     */
    @Override
    public Expressive base() {
        return base;
    }

    @Override
    public Ticker eventually() {
        return base.eventually();
    }

    @Override
    public void poll(Ticker ticker, Condition condition) {
        base.poll(ticker, condition);
    }
}
