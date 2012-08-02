package com.dhemery.polling;


/**
 * Polls until a condition is satisfied.
 * 
 * @author Dale Emery
 */
public interface Poller {
    void poll(Condition condition);
}
