package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Indicates that the poll timer expired before the subject satisfied the criteria.
 * @author Dale Emery
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
		super(String.format("Timed out waiting until %s", condition));
    }
}
