package com.dhemery.polling.events;

import com.dhemery.core.Condition;
import org.hamcrest.StringDescription;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class ConditionSatisfied {
    private final String description;
    private final int failureCount;

    public ConditionSatisfied(Condition condition, int failureCount) {
        description = StringDescription.asString(condition);
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return description; }

    /**
     * The number of times this condition polled unsatisfied before polling satisfied.
     */
    public int failureCount() { return failureCount; }
}
