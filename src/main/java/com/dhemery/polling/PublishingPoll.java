package com.dhemery.polling;

import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;

/**
 * A poll that publishes each evaluation result.
 */
public class PublishingPoll implements Poll {
    private final Publisher publisher;
    private final Poll poll;

    /**
     * Create a poll that publishes each evaluation result.
     */
    public PublishingPoll(Publisher publisher, Poll poll) {
        this.publisher = publisher;
        this.poll = poll;
    }

    @Override
    public void until(Condition condition) {
        poll.until(new PublishingCondition(publisher, condition));
    }

    @Override
    public <S> void until(S subject, Matcher<? super S> criteria) {
        until(new MatcherCondition(subject, criteria));
    }
}
