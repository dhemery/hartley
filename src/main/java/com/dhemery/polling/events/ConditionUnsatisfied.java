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

    /**
     * The polled condition.
     * <p>
     * <strong>WARNING:</strong>
     * Calling {@link Condition#isSatisfied()} on the condition may change its state,
     * which may confuse any subscribers to which this notifaction is subsequently sent.
     * </p>
     * @return the polled condition
     */
    public Condition condition() { return condition; }

    /**
     * The number of times this condition polled unsatisfied,
     * including the poll reported by this notification.
     */
    public long failureCount() { return failureCount; }
}
