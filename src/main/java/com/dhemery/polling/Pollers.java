package com.dhemery.polling;

import com.dhemery.publishing.Publisher;

/**
 * Factory methods to create {@link Poller} objects.
 */
public class Pollers {
    /**
     * Create a poller that simply evaluates the condition.
     */
    public static Poller simplePoller() {
        return new EvaluatingPoller(new SimplePollEvaluator());
    }

    /**
     * Create a poller that publishes the result of each evaluation.
     */
    public static Poller publishedWith(Publisher publisher) {
        return new EvaluatingPoller(new PublishingPollEvaluator(publisher));
    }
}
