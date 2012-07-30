package com.dhemery.polling.events;

import com.dhemery.polling.Condition;

/**
 * Reports that a condition was unsatisfied during a poll.
 */
public class Unsatisfied {
    private final Condition condition;

    public Unsatisfied(Condition condition) {
        this.condition = condition;
    }
    public Condition condition() { return condition; }
}
