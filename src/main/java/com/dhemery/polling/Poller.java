package com.dhemery.polling;


/**
 * Polls until a condition is satisfied.
 */
public interface Poller {
    /**
     * Poll until the condition is satisfied.
     */
    void poll(Ticker ticker, Condition condition);
}
