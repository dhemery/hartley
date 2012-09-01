package com.dhemery.polling;

import com.dhemery.publishing.Publisher;

public class Pollers {
    public static Poller simplePoller() {
        return new EvaluatingPoller(new SimplePollEvaluator());
    }

    public static Poller publishedWith(Publisher publisher) {
        return new EvaluatingPoller(new PublishingPollEvaluator(publisher));
    }
}
