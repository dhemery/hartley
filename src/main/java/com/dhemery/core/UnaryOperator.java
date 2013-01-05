package com.dhemery.core;

/**
 * An operator that operates on a single value, and produces a value of the same type.
 * @param <T> the type of operand
 */
public interface UnaryOperator<T> {
    T operate(T operand);
}
