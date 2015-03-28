package com.dhemery.polling.events;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

/**
 * Reports that a condition was satisfied during a poll.
 */
public class ConditionSatisfied implements SelfDescribing {
    private final Condition condition;
    private final int failureCount;

    public ConditionSatisfied(Condition condition, int failureCount) {
        this.condition = condition;
        this.failureCount = failureCount;
    }

    /**
     * A description of the condition.
     */
    public String description() { return StringDescription.asString(condition); }

    /**
     * The number of times this condition polled unsatisfied before polling satisfied.
     */
    public int failureCount() { return failureCount; }

    @Override
    public void describeTo(Description description) {
        description.appendText("poll was satisfied that ").appendValue(description())
                .appendText(" after ").appendValue(failureCount()).appendText(" failures");
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
