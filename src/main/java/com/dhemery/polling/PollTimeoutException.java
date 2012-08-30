package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Indicates that a poll's ticker expired before the polled condition was satisfied.
 */
public class PollTimeoutException extends RuntimeException {
    public PollTimeoutException(Condition condition) {
        super(timeoutMessageFor(condition));
    }

    private static String timeoutMessageFor(Condition condition) {
        Description description = new StringDescription();
        description.appendText("Timed out waiting until")
                .appendDescriptionOf(condition)
                .appendText("\n   Final poll failed because");
        condition.describeDissatisfactionTo(description);
        return description.toString();
    }
}
