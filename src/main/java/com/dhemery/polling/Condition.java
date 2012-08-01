package com.dhemery.polling;

import org.hamcrest.SelfDescribing;

/**
 * A condition that may be satisfied.
 */
public interface Condition extends SelfDescribing {
    /**
     * Evaluate the condition.
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();
}
