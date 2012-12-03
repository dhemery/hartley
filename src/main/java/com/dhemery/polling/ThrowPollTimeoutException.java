package com.dhemery.polling;

import com.dhemery.core.Condition;

public class ThrowPollTimeoutException implements Runnable {
    private final Condition condition;

    public ThrowPollTimeoutException(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void run() {
        throw new PollTimeoutException(condition);
    }
}
