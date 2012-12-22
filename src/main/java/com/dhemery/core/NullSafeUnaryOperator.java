package com.dhemery.core;

/**
 * A unary operator that allows subclasses to operate on the operand without checking for null.
 * @param <T> the type of operand
 */
public abstract class NullSafeUnaryOperator<T> implements SelfDescribingUnaryOperator<T> {
    @Override
    public T operate(T operand) {
        return operand == null ? defaultValue() : safelyOperate(operand);
    }

    /**
     * Operate on the operand, which is guaranteed not to be {@code null}.
     * <p>This implementation returns its operand.
     * Override this method to operate on the non-null operand.
     * </p>
     * @param operand the non-null operand to operate on
     * @return the result of applying the operator to the operand
     */
    protected T safelyOperate(T operand) { return operand; };

    /**
     * Return the value that the operator would produce for a {@code null} operand.
     * <p>This implementation returns {@code null}.
     * Override this method to provide a value for a {@code null} operand.
     * </p>
     * @return the result that the operator would produce for a {@code null} operand
     */
    protected T defaultValue() { return null; };
}
