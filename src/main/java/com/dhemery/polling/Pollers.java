package com.dhemery.polling;

import com.dhemery.publishing.Publisher;

/**
 * Factory methods for common pollers.
 */
public class Pollers {
    /**
     * Create a simple poller that knows how to poll conditions.
     */
    public static Poller defaultPoller() {
        return new EvaluatingPoller(new SimplePollEvaluator());
    }

    /**
     * Create a poller that publishes polling events through {@code publisher}.
     * See {@link com.dhemery.polling.events} for descriptions
     * of the polling events published by the poller.
     * @param publisher the publisher through which to publish events
     * @return a poller that publishes through {@code publisher}
     */
    public static Poller publishedWith(Publisher publisher) {
        PollEvaluator simpleEvaluator = new SimplePollEvaluator();
        PollEvaluator publishingEvaluator = new PublishingPollEvaluator(publisher, simpleEvaluator);
        return new EvaluatingPoller(publishingEvaluator);
    }
}
