package com.dhemery.polling;

import com.dhemery.core.Condition;

public class SimplePollEvaluator implements PollEvaluator {
    @Override
    public boolean evaluate(Condition condition, int pollCount) {
        return condition.isSatisfied();
    }

    @Override
    public void fail(Condition condition) {
        throw new PollTimeoutException(condition);
    }
}
