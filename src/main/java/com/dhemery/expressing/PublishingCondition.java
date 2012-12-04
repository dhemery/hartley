package com.dhemery.expressing;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionDissatisfied;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Description;

/**
 * Wraps a condition to publish the result of its evaluations.
 */
public class PublishingCondition implements Condition {
    private final Condition condition;
    private final Publisher publisher;
    private int failureCount;

    /**
     * Wraps the given condition to publish the results of its evaluations.
     */
    public PublishingCondition(Condition condition, Publisher publisher) {
        this.condition = condition;
        this.publisher = publisher;
    }

    @Override
    public boolean isSatisfied() {
        boolean isSatisfied = condition.isSatisfied();
        if(isSatisfied) publisher.publish(new ConditionSatisfied(condition, failureCount));
        else {
            failureCount++;
            publisher.publish(new ConditionDissatisfied(condition, failureCount));
        }
        return isSatisfied;
    }

    @Override
    public void describeTo(Description description) {
        condition.describeTo(description);
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        condition.describeDissatisfactionTo(description);
    }
}
