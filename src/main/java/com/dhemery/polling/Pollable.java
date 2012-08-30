package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.publishing.Publisher;

public abstract class Pollable implements Condition {
    private final Publisher publisher;

    public Pollable(Publisher publisher) {
        this.publisher = publisher;
    }

    void poll(Ticker ticker) {
        ticker.start();
        int failureCount = 0;
        while (!ticker.isExpired()) {
            if(isSatisfied()) {
                publisher.publish(successNotification(failureCount));
                return;
            }
            publisher.publish(failureNotification(failureCount));
            failureCount++;
            ticker.tick();
        }
        throw new PollTimeoutException(this);
    }

    protected abstract Object failureNotification(int failureCount);
    protected abstract Object successNotification(int failureCount);
}
