package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;

public class PublishingPollEvaluator implements PollEvaluator {
    private final Publisher publisher;

    public PublishingPollEvaluator(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean evaluate(Condition condition, int pollCount) {
        boolean satisfied = condition.isSatisfied();
        if(satisfied) {
            publisher.publish(new ConditionSatisfied(condition, pollCount - 1));
        } else {
            publisher.publish(new ConditionUnsatisfied(condition, pollCount));
        }
        return satisfied;
    }

    @Override
    public void fail(Condition condition) {
        throw new PollTimeoutException(condition);
    }
}
