package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * Evaluates whether a condition is currently satisfied.
 */
public interface Condition extends SelfDescribing {
    /**
     * Evaluate whether the condition is satisfied.
     * <p>
     * Note: Evaluating a condition may change its state.
     * </p>
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();

    /**
     * Describe the condition when satisfied.
     * <p>
     * This method should not change the condition's state.
     * </p>
     */
    @Override
    void describeTo(Description description);
    void describeFailureTo(Description description);
}
