package com.dhemery.expressions;

import java.util.function.Consumer;


/**
 * A {@link Consumer} with a fixed description.
 * The {@code toString()} method
 * returns the fixed description.
 * Each composed consumer
 * created by this consumer
 * receives a description of the composition.
 * @param <T> the type of the input to the consumer
 */
public class DescriptiveConsumer<T> extends Descriptive implements Consumer<T> {
    private final Consumer<? super T> consumer;

    /**
     * Create a {@link Consumer}
     * with the given description
     * and underlying consumer.
     */
    public DescriptiveConsumer(String description, Consumer<? super T> consumer) {
        super(description);
        this.consumer = consumer;
    }

    /**
     * {@inheritDoc}
     * This implementation delegates to the underlying consumer.
     */
    @Override
    public void accept(T t) {
        consumer.accept(t);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The composed consumer's description
     * describes this consumer,
     * the {@code after} consumer,
     * and their composition.
     * </p>
     */
    @Override
    public Consumer<T> andThen(Consumer<? super T> after) {
        return new DescriptiveConsumer<>(describedAs("(", this, "and then", after, ")"), Consumer.super.andThen(after));
    }
}
