package com.dhemery.polling.events;

import com.dhemery.core.Condition;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class ConditionSatisfied {
    private final Condition condition;
    private final long failureCount;

    public ConditionSatisfied(Condition condition, long failureCount) {
        this.condition = condition;
        this.failureCount = failureCount;
    }
    public Condition condition() { return condition; }
    public long failureCount() { return failureCount; }
}
