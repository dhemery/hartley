package com.dhemery.polling;

/**
 * A condition that may be satisfied.
 */
public interface Condition {
    /**
     * Evaluate the condition.
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();
}
