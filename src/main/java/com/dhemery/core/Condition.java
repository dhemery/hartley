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
     * Note: This method should not change the condition's state.
     * </p>
     * @param description the description to which to describe this condition
     */
    @Override
    void describeTo(Description description);

    /**
     * Describe this condition's most recent dissatisfaction.
     * This method is meaningful only if the most recent {@code isSatisfied()} returned false.
     * <p>
     * Note: This method should not change the condition's state.
     * </p>
     * @param description the description to which to describe the dissatisfaction
     */
    void describeDissatisfactionTo(Description description);
}
