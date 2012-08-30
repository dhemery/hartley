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

    /**
     * The polled condition.
     * <p>
     * <strong>IMPORTANT:</strong>
     * Calling any method that would change the state of the condition,
     * such as {@link Condition#isSatisfied()},
     * may create unpredictable results.
     * </p>
     * @return the polled condition
     */
    public Condition condition() { return condition; }

    /**
     * The number of times this condition polled unsatisfied before polling satisfied.
     */
    public long failureCount() { return failureCount; }
}
