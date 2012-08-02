package com.dhemery.polling;

import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;

/**
 * Decorates a poll evaluator to publish the result of each evaluation.
 */
public class PublishingPollEvaluator implements PollEvaluator {
    private final Publisher publisher;
    private final PollEvaluator evaluator;

    /**
     * Decorate the poll evaluator to publish the result of each evaluation.
     */
    public PublishingPollEvaluator(Publisher publisher, PollEvaluator evaluator) {
        this.publisher = publisher;
        this.evaluator = evaluator;
    }

    /**
     * Publish and report the result of evaluating the condition.
     * @return whether the condition is satisfied
     */
    @Override
    public boolean evaluate(Condition condition, long failureCount) {
        boolean satisfied = evaluator.evaluate(condition, failureCount);
        Object notification = satisfied ? new ConditionSatisfied(condition, failureCount) : new ConditionUnsatisfied(condition, failureCount + 1);
        publisher.publish(notification);
        return satisfied;
    }
}
