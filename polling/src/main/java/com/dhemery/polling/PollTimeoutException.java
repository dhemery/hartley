package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Indicates that a poll's ticker expired before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(explainTimeoutOf(condition));
    }

    private static String explainTimeoutOf(Condition condition) {
        Description description = new StringDescription();
        description.appendText("Timed out waiting until ");
        condition.describeTo(description);
        description.appendText("\n   because ");
        condition.describeDissatisfactionTo(description);
        return description.toString();
    }
}
