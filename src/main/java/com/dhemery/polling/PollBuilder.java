package com.dhemery.polling;

import com.dhemery.publishing.Publisher;
import com.sun.istack.internal.Builder;

/**
 * Incrementally constructs a poll.
 */
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

    /**
     * Create a poll builder with the default poll timer and no publisher.
     */
    public PollBuilder(){}

    /**
     * Supply the duration for the poll.
     * This causes the builder
     * to use the separately supplied poll duration and interval
     * instead of any previously supplied poll timer.
     * @return this builder
     */
    public PollBuilder duration(long duration) {
        this.duration = duration;
        timerBuilder = DEFAULT_TIMER_BUILDER;
        return this;
    }

    /**
     * Supply the polling interval.
     * This causes the builder
     * to use the separately supplied poll duration and interval
     * instead of any previously supplied poll timer.
     * @return this builder
     */
    public PollBuilder interval(long interval) {
        this.interval = interval;
        timerBuilder = DEFAULT_TIMER_BUILDER;
        return this;
    }

    /**
     * Supply a publisher that will publish events related to this poll.
     * @return this builder
     */
    public PollBuilder publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    /**
     * Supply the poll's timer.
     * This causes the builder to use the timer
     * rather than any poll duration and interval supplied earlier.
     * @param timer the timer to use for polling
     * @return this builder
     */
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
