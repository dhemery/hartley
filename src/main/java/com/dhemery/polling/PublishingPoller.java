package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionDissatisfied;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.publishing.Publisher;

public class PublishingPoller implements Poller {
    private final Publisher publisher;
    private int failureCount = 0;

    public PublishingPoller(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();

        do {
            if(condition.isSatisfied()) {
                publisher.publish(new ConditionSatisfied(condition, failureCount));
                return;
            }
            failureCount++;
            publisher.publish(new ConditionDissatisfied(condition, failureCount));
            ticker.tick();
        } while(!ticker.isExpired());

        throw new PollTimeoutException(condition);
    }
}
