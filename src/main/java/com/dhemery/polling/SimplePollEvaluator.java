package com.dhemery.polling;

/**
 * Evaluates conditions by asking the conditions whether they are satisfied.
 */
public class SimplePollEvaluator implements PollEvaluator {
    /**
     * Report whether the condition is satisfied.
     */
    @Override
    public boolean evaluate(Condition condition, long ignored) {
        return condition.isSatisfied();
    }
}
