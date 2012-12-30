package com.dhemery.core;

/**
 * An operator that yields its operand.
 * @param <T> the type of operand
 */
public class IdentityOperator<T> extends Named implements UnaryOperator<T> {
    public IdentityOperator(String name) {
        super(name);
    }

    @Override
    public T operate(T operand) {
        return operand;
    }
}
