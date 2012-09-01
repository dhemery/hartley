package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Polls until a condition is satisfied or a ticker expires.
 */
public interface Poller {
    /**
     * Poll until the condition is satisfied or the ticker expires.
     * @param ticker a ticker to control the polling
     * @param condition the condition to poll
     * @throws PollTimeoutException if the ticker expires before the condition is satisfied
     */
    void poll(Ticker ticker, Condition condition);
}
