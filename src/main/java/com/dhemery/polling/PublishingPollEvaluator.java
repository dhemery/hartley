package com.dhemery.polling;

import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;

public class PublishingPollEvaluator implements PollEvaluator {
    private final Publisher publisher;

    public PublishingPollEvaluator(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean evaluate(Condition condition, long failureCount) {
        boolean satisfied = condition.isSatisfied();
        Object notification = satisfied ? new ConditionSatisfied(condition, failureCount) : new ConditionUnsatisfied(condition, failureCount + 1);
        publisher.publish(notification);
        return satisfied;
    }
}
