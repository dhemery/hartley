package com.dhemery.polling;

import com.dhemery.polling.events.Satisfied;
import com.dhemery.polling.events.Unsatisfied;
import com.dhemery.publishing.Publisher;

/**
 * Wraps a condition
 * and publishes the result of each evaluation of the condition.
 */
public class PublishingCondition implements Condition {
    private final Publisher publisher;
    private final Condition condition;

    /**
     * Wrap the given condition
     * and publish the result of each evaluation of the condition.
     */
    public PublishingCondition(Publisher publisher, Condition condition) {
        this.publisher = publisher;
        this.condition = condition;
    }

    /**
     * Evaluate the condition and publish the result.
     * @return whether the condition is satisfied
     */
    @Override
    public boolean isSatisfied() {
        boolean isSatisfied = condition.isSatisfied();
        Object event = isSatisfied ? new Satisfied(condition) : new Unsatisfied(condition);
        publisher.publish(event);
        return isSatisfied;
    }
}
