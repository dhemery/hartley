package com.dhemery.core;

/**
 * Evaluates whether a condition is currently satisfied.
 */
public interface Condition{
    /**
     * Evaluate whether the condition is satisfied.
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();
}
