package com.dhemery.polling.events;

import com.dhemery.core.Condition;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class ConditionSatisfied {
    private final Condition condition;
    private final int failureCount;

    public ConditionSatisfied(Condition condition, int failureCount) {
        this.condition = condition;
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return condition.toString(); }

    /**
     * The number of times this condition polled unsatisfied before polling satisfied.
     */
    public int failureCount() { return failureCount; }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("poll was satisfied that ")
                .append(description())
                .append(" after ")
                .append(failureCount())
                .append(" failures")
                .toString();
    }
}
