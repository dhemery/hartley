package com.dhemery.polling;

import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Description;

public class PublishingCondition implements Condition {
    private final Publisher publisher;
    private final Condition condition;

    public PublishingCondition(Publisher publisher, Condition condition) {
        this.publisher = publisher;
        this.condition = condition;
    }

    @Override
    public boolean isSatisfied() {
        boolean isSatisfied = condition.isSatisfied();
        Object notification = isSatisfied ? new ConditionSatisfied(condition) : new ConditionUnsatisfied(condition);
        publisher.publish(notification);
        return isSatisfied;
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        condition.describeDissatisfactionTo(description);
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
