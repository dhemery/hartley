package com.dhemery.polling;

import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;

/**
 * A poller that publishes each evaluation result.
 */
public class PublishingPoller implements Poller {
    private final Publisher publisher;
    private final Poller poller;

    /**
     * Create a poller that uses the given publisher and timer.
     */
    public PublishingPoller(Publisher publisher, Poller poller) {
        this.publisher = publisher;
        this.poller = poller;
    }

    @Override
    public void poll(Condition condition) {
        poller.poll(new PublishingCondition(publisher, condition));
    }

    @Override
    public <S> void poll(S subject, Matcher<? super S> criteria) {
        poll(new MatcherCondition(subject, criteria));
    }
}
