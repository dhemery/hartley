package com.dhemery.polling;

/**
 * Indicates that the poll ticker expired before the condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(String.format("Timed out waiting until %s", condition));
    }
}
