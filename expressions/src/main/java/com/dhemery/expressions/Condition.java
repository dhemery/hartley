package com.dhemery.expressions;

import java.util.Optional;

/**
 * Represents a condition whose satisfaction may change over time.
 */
@FunctionalInterface
public interface Condition {
    /**
     * Report whether the condition is satisfied.
     * @return {@code true} if the condition is satisfied,
     * otherwise {@code} false.
     */
    boolean isSatisfied();

    /**
     * Optionally diagnose the most recent dissatisfaction.
     * @return the optional diagnosis.
     */
    default Optional<String> diagnosis() { return Optional.empty(); }
}
