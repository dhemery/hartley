package com.dhemery.expressions;

import java.util.function.Predicate;

/**
 * A {@link Predicate} with a fixed description.
 * The {@code toString()} method
 * returns the fixed description.
 * Each composed predicate
 * created by this predicate
 * receives a description of the composition.
 * @param <T> the type of the input to the consumer
 */
public class DescriptivePredicate<T> extends Descriptive implements Predicate<T> {
    private final Predicate<? super T> predicate;

    /**
     * Create a {@link Predicate}
     * with the given description
     * and underlying predicate.
     */
    public DescriptivePredicate(String description, Predicate<? super T> predicate) {
        super(description);
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     * This implementation delegates to the underlying consumer.
     */
    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The composed predicate's description
     * describes this predicate,
     * the {@code other} predicate,
     * and their composition.
     * </p>
     */
    @Override
    public Predicate<T> and(Predicate<? super T> other) {
        return new DescriptivePredicate<>(describedAs("(", this, "and", other, ")"), Predicate.super.and(other));
    }

    /**
     * {@inheritDoc}
     * <p>
     * The composed predicate's description
     * describes this predicate,
     * the {@code other} predicate,
     * and their composition.
     * </p>
     */
    @Override
    public Predicate<T> or(Predicate<? super T> other) {
        return new DescriptivePredicate<>(describedAs("(", this, "or", other, ")"), Predicate.super.or(other));
    }

    /**
     * {@inheritDoc}
     * <p>
     * The composed predicate's description
     * is this predicate's description
     * with the word "not" prepended.
     * </p>
     */
    @Override
    public Predicate<T> negate() {
        return new DescriptivePredicate<>(describedAs("not", this), Predicate.super.negate());
    }
}
