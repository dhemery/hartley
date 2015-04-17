package com.dhemery.expressions;

import java.util.Optional;

/**
 * A {@link Condition} with a fixed description.
 * The {@code toString()} method
 * returns the fixed description.
 */
public class DescriptiveCondition extends Descriptive implements Condition {
    private final Condition condition;

    /**
     * Create a {@link Condition}
     * with the given description
     * and underlying condition.
     */
    public DescriptiveCondition(String description, Condition condition) {
        super(description);
        this.condition = condition;
    }

    /**
     * {@inheritDoc}
     * This implementation delegates to the underlying condition.
     */
    @Override
    public boolean isSatisfied() {
        return condition.isSatisfied();
    }

    /**
     * {@inheritDoc}
     * This implementation delegates to the underlying condition.
     */
    @Override
    public Optional<String> diagnosis() {
        return condition.diagnosis();
    }
}
