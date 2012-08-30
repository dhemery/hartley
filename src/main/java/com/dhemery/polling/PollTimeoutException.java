package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Indicates that a poll's ticker expired before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(String.format("Timed out waiting until %s", condition));
    }
}
