package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.polling.events.ConditionUnsatisfied;
import com.dhemery.publishing.Publisher;

public class PublishingPoller implements Poller {
    private final Publisher publisher;

    public PublishingPoller(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();
        int failureCount = 0;
        while (!ticker.isExpired()) {
            if(condition.isSatisfied()) {
                publishSuccess(condition, failureCount);
                return;
            }
            failureCount++;
            publishFailure(condition, failureCount);
            ticker.tick();
        }
        throw new PollTimeoutException(condition);
    }

    private void publishFailure(Condition condition, int failureCount) {
        publisher.publish(new ConditionUnsatisfied(condition, failureCount));
    }

    private void publishSuccess(Condition condition, int failureCount) {
        publisher.publish(new ConditionSatisfied(condition, failureCount));
    }
}
