package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionDissatisfied;
import com.dhemery.publishing.Publisher;

/**
 * A poll evaluator that publishes the result of each evaluation.
 */
public class PublishingPollEvaluator implements PollEvaluator {
    private final Publisher publisher;
    private int failureCount = 0;

    /**
     * Create a poll evaluator that publishes the result of each evaluation through the publisher.
     */
    public PublishingPollEvaluator(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Evaluate the condition and publish the result.
     * The result is published as either a {@link ConditionSatisfied} or {@link ConditionDissatisfied}
     * event.
     *
     * @param condition the condition to evaluate
     * @return whether the condition is satisfied
     * @see ConditionSatisfied
     * @see ConditionDissatisfied
     */
    @Override
    public boolean evaluate(Condition condition) {
        boolean satisfied = condition.isSatisfied();
        if(satisfied) {
            publisher.publish(new ConditionSatisfied(condition, failureCount));
        } else {
            failureCount++;
            publisher.publish(new ConditionDissatisfied(condition, failureCount));
        }
        return satisfied;
    }

    /**
     * Throw a {@link PollTimeoutException} indicating that the poll timed out before the condition was satisfied.
     */
    @Override
    public void fail(Condition condition) {
        throw new PollTimeoutException(condition);
    }
}
