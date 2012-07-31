package com.dhemery.polling;

import com.dhemery.publishing.Publisher;
import com.sun.istack.internal.Builder;

public class PollBuilder implements Builder<Poll> {
    private static final long DEFAULT_POLLING_DURATION = 1000;
    private static final long DEFAULT_POLLING_INTERVAL = 30000;
    private Builder<PollTimer> DEFAULT_TIMER_BUILDER = new Builder<PollTimer>() {
        @Override
        public PollTimer build() {
            return new SystemClockPollTimer(duration, interval);
        }
    };
    private long duration = DEFAULT_POLLING_DURATION;
    private long interval = DEFAULT_POLLING_INTERVAL;
    private Publisher publisher = new Publisher() {
        @Override public void publish(Object publication) {}
    };
    private Builder<PollTimer> timerBuilder = DEFAULT_TIMER_BUILDER;

    public PollBuilder duration(long duration) {
        this.duration = duration;
        timerBuilder = DEFAULT_TIMER_BUILDER;
        return this;
    }

    public PollBuilder interval(long interval) {
        this.interval = interval;
        timerBuilder = DEFAULT_TIMER_BUILDER;
        return this;
    }

    public PollBuilder publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public PollBuilder timer(final PollTimer timer) {
        timerBuilder = new Builder<PollTimer>(){
            @Override
            public PollTimer build() {
                return timer;
            }
        };
        return this;
    }

    @Override
    public Poll build() {
        return new PublishingPoll(publisher, new TimedPoll(timerBuilder.build()));
    }
}
