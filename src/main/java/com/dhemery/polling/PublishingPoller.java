package com.dhemery.polling;

import com.dhemery.publishing.Publisher;

public class PublishingPoller implements Poller {
    private final Publisher publisher;
    private final Poller poller;

    public PublishingPoller(Publisher publisher, Poller poller) {
        this.publisher = publisher;
        this.poller = poller;
    }

    @Override
    public void poll(Condition condition) {
        poller.poll(new PublishingCondition(publisher, condition));
    }
}
