package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * Evaluates a condition during polling.
 */
public interface PollEvaluator {
    /**
     * Evaluate whether the condition is satisfied.
     * @param condition the condition to evaluate
     * @param pollCount the number of times this poll has evaluated the condition (including this evaluation)
     * @return whether the condition is satisfied
     */
    boolean evaluate(Condition condition, int pollCount);

    /**
     * Throw an exception indicating that the poll ended with the condition unsatisfied.
     * @param condition the condition that was polled
     */
    void fail(Condition condition);
}
