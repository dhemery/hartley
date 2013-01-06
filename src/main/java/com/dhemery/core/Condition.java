package com.dhemery.core;

/**
 * A condition that may be satisfied or dissatisfied.
 */
public interface Condition {
    /**
     * Evaluate whether the condition is satisfied.
     * <p>
     * Note: Evaluating a condition may change its state.
     * </p>
     * @return whether the condition is satisfied
     */
    boolean isSatisfied();

    /**
     * Explain the condition's most recent dissatisfaction.
     * This method is meaningful only if the most recent {@code isSatisfied()} returned false.
     * <p>
     * Note: This method should not change the condition's state.
     * </p>
     */
    String explainDissatisfaction();
}
