package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * A poll evaluator that evaluates whether the condition is satisfied.
 */
public class SimplePollEvaluator implements PollEvaluator {
    /**
     * Report whether the condition is satisfied.
     * @param condition the condition being polled
     * @param pollCount is ignored
     * @return whether the condition is satisfied
     */
    @Override
    public boolean evaluate(Condition condition, int pollCount) {
        return condition.isSatisfied();
    }

    /**
     * Throw a {@link PollTimeoutException} indicating that the poll timed out before the condition was satisfied.
     */
    @Override
    public void fail(Condition condition) {
        throw new PollTimeoutException(condition);
    }
}
