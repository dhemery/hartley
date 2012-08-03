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
     * Describe the condition when dissatisfied.
     */
    void describeDissatisfactionTo(Description description);
}
