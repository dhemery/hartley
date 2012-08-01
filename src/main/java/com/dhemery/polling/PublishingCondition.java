package com.dhemery.polling;

import com.dhemery.polling.events.Satisfied;
import com.dhemery.polling.events.Unsatisfied;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * Wraps a condition and publishes the result of each evaluation.
 */
public class PublishingCondition implements Condition, SelfDescribing {
    private final Publisher publisher;
    private final Condition condition;

    /**
     * Wrap the condition
     * and publish the result of each evaluation.
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

    @Override
    public void describeTo(Description description) {
        condition.describeTo(description);
    }

    @Override
    public String toString() {
        return condition.toString();
    }
}
