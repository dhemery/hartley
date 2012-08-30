package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;

public class PollableCondition extends Pollable {
    private final Condition condition;

    public PollableCondition(Publisher publisher, Condition condition) {
        super(publisher);
        this.condition = condition;
    }

    @Override
    public boolean isSatisfied() {
        return condition.isSatisfied();
    }

    @Override
    protected Object failureNotification(int failureCount) {
        return new ConditionSatisfied(condition, failureCount);
    }

    @Override
    protected Object successNotification(int failureCount) {
        return new ConditionUnsatisfied(condition, failureCount);
    }
}
