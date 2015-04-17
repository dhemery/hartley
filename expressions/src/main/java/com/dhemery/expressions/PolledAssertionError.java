package com.dhemery.expressions;

import java.util.StringJoiner;

public class PolledAssertionError extends AssertionError {
    public PolledAssertionError(PollingSchedule schedule, Condition condition) {
        super(explanation(schedule, condition));
    }

    private static String explanation(PollingSchedule schedule, Condition condition) {
        return new StringJoiner(" ")
                .add("Expected:")
                .add(String.valueOf(condition))
                .add("\n     but: timed out after polling")
                .add(String.valueOf(schedule))
                .toString();
    }
}
