package com.dhemery.polling.events;

import com.dhemery.core.Condition;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class Satisfied {
    private final Condition condition;

    public Satisfied(Condition condition) {
        this.condition = condition;
    }
    public Condition condition() { return condition; }
}
