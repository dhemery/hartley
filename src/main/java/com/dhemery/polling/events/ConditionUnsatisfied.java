package com.dhemery.polling.events;

import com.dhemery.polling.Condition;

/**
 * Reports that a sample mismatched its criteria during a poll.
 */
public class ConditionUnsatisfied {
    private final Condition condition;

    public ConditionUnsatisfied(Condition condition) {
        this.condition = condition;
    }
    public Condition condition() { return condition; }
}
