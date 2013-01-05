package com.dhemery.core;

/**
 * An operator that describes itself with a fixed name.
 * @param <T> the type of operand
 */
public abstract class NamedUnaryOperator<T> extends Named implements UnaryOperator<T> {
    /**
     * Create an operator with a fixed name.
     */
    public NamedUnaryOperator(String name) {
        super(name);
    }
}
