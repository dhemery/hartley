package com.dhemery.core;

/**
 * An operator described by a fixed name.
 * @param <T> the type of operand
 */
public abstract class NamedUnaryOperator<T> extends Named implements UnaryOperator<T> {
    /**
     * Create a named operator.
     */
    public NamedUnaryOperator(String name) {
        super(name);
    }
}
