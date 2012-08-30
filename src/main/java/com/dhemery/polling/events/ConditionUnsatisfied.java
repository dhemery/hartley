package com.dhemery.polling.events;

import com.dhemery.core.Condition;

/**
 * Reports that a sample mismatched its criteria during a poll.
 */
public class ConditionUnsatisfied {
    private final Condition condition;
    private final long failureCount;

    public ConditionUnsatisfied(Condition condition, long failureCount) {
        this.condition = condition;
        this.failureCount = failureCount;
    }
    public Condition condition() { return condition; }
    public long failureCount() { return failureCount; }
}
