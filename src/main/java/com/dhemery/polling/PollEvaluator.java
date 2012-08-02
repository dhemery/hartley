package com.dhemery.polling;

/**
 * Evaluates conditions during polling.
 */
public interface PollEvaluator {
    /**
     * Evaluate whether the condition is satisfied.
     * @param condition the condition to evaluate
     * @param failureCount the number of times the condition was unsatisfied prior to this evaluation
     * @return whether the condition is satisfied
     */
    boolean evaluate(Condition condition, long failureCount);
}
