package com.dhemery.core;

import org.hamcrest.SelfDescribing;

import java.util.function.Consumer;

/**
 * A {@link SelfDescribing SelfDescribing} {@link Consumer Consumer}.
 * <p>
 * This is an obsolete interface,
 * designed before Java 8 introduced functional interfaces.
 * Do not use this interface for new self-describing consumers.
 * Instead, implement
 * both {@link Consumer Consumer}
 * and {@link SelfDescribing SelfDescribing}.
 * If you do not need the consumer to describe itself,
 * prefer {@link Consumer Consumer}.
 * </p>
 */
public interface Action<T> extends Consumer<T>, SelfDescribing {
    /**
     * Performs this operation on the given argument.
     */
    void actOn(T t);

    /**
     * Delegates to {@link #actOn(T)}.
     * <p>
     * NOTE: Do not override this method.
     * If you need this method to do something else,
     * implement {@link Consumer Consumer} instead.
     */
    default void accept(T subject) { actOn(subject); }
}
