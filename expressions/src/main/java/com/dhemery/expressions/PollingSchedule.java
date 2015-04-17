package com.dhemery.expressions;

import java.time.Duration;
import java.util.StringJoiner;

import static java.time.temporal.ChronoUnit.SECONDS;

public class PollingSchedule {
    private final Duration duration;
    private final Duration interval;

    /**
     * Create a schedule to poll with the given interval and duration.
     * @param duration the duration to poll
     * @param interval the polling interval
     */
    public PollingSchedule(Duration duration, Duration interval) {
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Create schedule to poll every second for the given duration.
     * @param duration the duration to poll
     */
    public PollingSchedule(Duration duration) {
        this(duration, Duration.of(1, SECONDS));
    }

    public Duration duration() { return duration; }
    public Duration interval() { return interval; }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add("every")
                .add(String.valueOf(interval))
                .add("for")
                .add(String.valueOf(duration))
                .toString();
    }
}
