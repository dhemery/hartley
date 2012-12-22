package com.dhemery.core;

public abstract class NamedUnaryOperator<T> extends Named implements SelfDescribingUnaryOperator<T> {
    /**
     * Create a named unary operator.
     */
    public NamedUnaryOperator(String name) {
        super(name);
    }
}
