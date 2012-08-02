package com.dhemery.polling.events;

import com.dhemery.polling.Condition;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class ConditionSatisfied {
    private final Condition condition;

    public ConditionSatisfied(Condition condition) {
        this.condition = condition;
    }
    public Condition condition() { return condition; }
}
