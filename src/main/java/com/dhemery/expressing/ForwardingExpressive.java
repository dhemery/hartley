package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.Ticker;

/**
 * An {@link Expressive} that forwards to another {@code Expressive}
 * to create default tickers and prepare conditions for polling.
 */
public class ForwardingExpressive extends AbstractExpressive {
    private final Expressive base;

    /**
     * Create an {@code Expressive}
     * that forwards to the source {@code Expressive}'s base
     * to create default tickers and prepare conditions for polling.
     */
    public ForwardingExpressive(Expressive source) {
        base = source.base();
    }

    /**
     * Return the base {@code Expressive} to which this {@code Expressove}
     * forwards to create default tickers and prepare conditions for polling.
     * on behalf of this {@code ForwardingExpressive}.
     */
    @Override
    public Expressive base() {
        return base;
    }

    @Override
    public Ticker createDefaultTicker() {
        return base.createDefaultTicker();
    }

    @Override
    public Condition prepareToPoll(Condition condition) {
        return base.prepareToPoll(condition);
    }
}
