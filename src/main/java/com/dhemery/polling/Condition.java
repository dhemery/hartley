package com.dhemery.polling;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * Evaluates whether a condition is currently satisfied.
 */
public interface Condition extends SelfDescribing {
    /**
     * Evaluate whether the condition is satisfied.
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();

    /**
     * Describe the most recent dissatisfaction.
     * This method should be called only when the most recent call to {@link #isSatisfied()} returned {@code false}.
     * If the method is called when the condition is satisfied, the result is undefined.
     * @param description the description to which to describe this condition
     */
    void describeDissatisfactionTo(Description description);
}
