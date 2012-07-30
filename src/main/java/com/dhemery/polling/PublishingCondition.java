package com.dhemery.polling;

import com.dhemery.polling.events.Satisfied;
import com.dhemery.polling.events.Unsatisfied;
import com.dhemery.publishing.Publisher;

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
        Object event = isSatisfied ? new Satisfied(condition) : new Unsatisfied(condition);
        publisher.publish(event);
        return isSatisfied;
    }
}
