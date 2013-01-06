package com.dhemery.expressing;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionDissatisfied;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.publishing.Publisher;

/**
 * Wraps a condition to publish the result of each evaluation.
 */
public class PublishingCondition implements Condition {
    private final Condition condition;
    private final Publisher publisher;
    private int failureCount = 0;

    /**
     * Wraps the given condition to publish the result of each evaluation.
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
    public String explainDissatisfaction() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
