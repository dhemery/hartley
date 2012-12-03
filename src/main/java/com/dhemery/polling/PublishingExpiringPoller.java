package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.publishing.Publisher;

public class PublishingExpiringPoller extends ExpiringPoller {
    private final Publisher publisher;

    public PublishingExpiringPoller(Ticker ticker, Runnable onExpiration, Publisher publisher) {
        super(ticker, onExpiration);
        this.publisher = publisher;
    }

    @Override
    public void poll(Condition condition) {
        super.poll(new PublishingCondition(condition, publisher));
    }
}
