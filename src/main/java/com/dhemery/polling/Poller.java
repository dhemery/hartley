package com.dhemery.polling;


/**
 * Polls until a condition is satisfied.
 * 
 * @author Dale Emery
 */
public interface Poller {
    /**
     * Poll until the condition is satisfied.
     */
    void poll(Condition condition);
}
