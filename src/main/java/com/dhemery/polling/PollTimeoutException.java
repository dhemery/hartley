package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Indicates that a poll's ticker expired before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(explainTimeoutOf(condition));
    }

    private static String explainTimeoutOf(Condition condition) {
        return new StringBuilder()
                .append("Timed out waiting until ")
                .append(condition)
                .toString();
    }
}
