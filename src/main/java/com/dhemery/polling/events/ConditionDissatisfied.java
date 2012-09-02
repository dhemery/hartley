package com.dhemery.polling.events;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Reports that a condition was dissatisfied during a poll.
 */
public class ConditionDissatisfied implements SelfDescribing {
    private final Condition condition;
    private final int failureCount;

    /**
     * Create a report that the condition was dissatisfied during a poll.
     * @param condition the condition that was dissatisfied
     * @param failureCount the number of times this condition has been dissatisfied during this poll,
     * including the evaluation reported by this notification
     */
    public ConditionDissatisfied(Condition condition, int failureCount) {
        this.condition = condition;
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return StringDescription.asString(condition); }

    /**
     * The reason the condition was dissatisfied.
     */
    public String reason() {
        Description description = new StringDescription();
        condition.describeDissatisfactionTo(description);
        return description.toString();
    }

    /**
     * The number of times this condition has been dissatisfied during this poll,
     * including the evaluation reported by this notification.
     */
    public int failureCount() { return failureCount; }

    @Override
    public void describeTo(Description description) {
        description.appendText("poll ").appendValue(failureCount)
                .appendText(" was dissatisfied that ").appendValue(description())
                .appendText(" because ").appendValue(reason());
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
