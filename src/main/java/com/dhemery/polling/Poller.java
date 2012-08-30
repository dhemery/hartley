package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Polls until a condition is satisfied or a ticker expires.
 */
public interface Poller {
    /**
     * Poll until the condition is satisfied or the ticker expires.
     */
    void poll(Ticker ticker, Condition condition);
}
