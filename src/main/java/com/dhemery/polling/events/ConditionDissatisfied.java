package com.dhemery.polling.events;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Reports that a condition was dissatisfied during a poll.
 */
public class ConditionDissatisfied {
    private final String description;
    private final String reason;
    private final long failureCount;

    /**
     * Create a report that the condition was dissatisfied during a poll.
     * @param condition the condition that was dissatisfied
     * @param failureCount the number of times this condition has been dissatisfied during this poll,
     * including the evaluation reported by this notification
     */
    public ConditionDissatisfied(Condition condition, long failureCount) {
        description = descriptionOf(condition);
        reason = failureDescriptionFor(condition);
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return description; }

    /**
     * The reason the condition was dissatisfied.
     */
    public String reason() { return reason; }

    /**
     * The number of times this condition has been dissatisfied during this poll,
     * including the evaluation reported by this notification.
     */
    public long failureCount() { return failureCount; }

    private static String descriptionOf(Condition condition) {
        return StringDescription.asString(condition);
    }

    private static String failureDescriptionFor(Condition condition) {
        Description description = new StringDescription();
        condition.describeDissatisfactionTo(description);
        return description.toString();
    }

}
