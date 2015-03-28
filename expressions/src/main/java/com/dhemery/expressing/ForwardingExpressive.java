package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.Ticker;

/**
 * An {@link Expressive} that forwards to another {@code Expressive}
 * to create default tickers and prepare conditions for polling.
 */
public class ForwardingExpressive extends Expressive {
    private final Expressive source;

    /**
     * Create an {@code Expressive}
     * that forwards to the source {@code Expressive}
     * to create default tickers and prepare conditions for polling.
     */
    public ForwardingExpressive(Expressive source) {
        this.source = source;
    }

    @Override
    public Ticker createDefaultTicker() {
        return source.createDefaultTicker();
    }

    @Override
    public Condition prepareToPoll(Condition condition) {
        return source.prepareToPoll(condition);
    }
}
