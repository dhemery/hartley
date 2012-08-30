package com.dhemery.polling;

import org.hamcrest.StringDescription;

/**
 * Indicates that a poll's ticker expired before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Pollable pollable) {
        super(String.format("Timed out waiting until %s", StringDescription.asString(pollable)));
    }
}
