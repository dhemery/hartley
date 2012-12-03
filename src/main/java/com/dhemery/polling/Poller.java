package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Repeatedly evaluate a condition until the condition is satisfied.
 * The mechanism for repeating is defined by the implementation.
 */
public interface Poller {
    /**
     * Repeatedly evaluate the condition until the condition is satisfied.
     */
    void poll(Condition condition);
}
