package com.dhemery.polling.events;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Reports that a sample mismatched its criteria during a poll.
 */
public class ConditionUnsatisfied {
    private final String description;
    private final String reason;
    private final long failureCount;

    public ConditionUnsatisfied(Condition condition, long failureCount) {
        description = descriptionOf(condition);
        reason = failureDescriptionFor(condition);
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return description; }

    /**
     * The reason the condition last failed.
     */
    public String reason() { return reason; }

    /**
     * The number of times this condition polled unsatisfied,
     * including the poll reported by this notification.
     */
    public long failureCount() { return failureCount; }

    private String descriptionOf(Condition condition) {
        return StringDescription.asString(condition);
    }

    private String failureDescriptionFor(Condition condition) {
        Description description = new StringDescription();
        condition.describeFailureTo(description);
        return description.toString();
    }

}
