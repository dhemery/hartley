package com.dhemery.expressions;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * Indicates that a poll timed out before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(explanation(condition, Optional.empty()));
    }

    public PollTimeoutException(PollingSchedule schedule, Condition condition) {
        super(explanation(condition, Optional.of(schedule)));
    }

    private static String explanation(Condition condition, Optional<PollingSchedule> schedule) {
        StringJoiner explanation = new StringJoiner(" ")
                .add("Timed out waiting for:").add(String.valueOf(condition));
        schedule.ifPresent(s -> explanation.add("\n    Polled:").add(String.valueOf(s)));
        return explanation.toString();
    }
}
