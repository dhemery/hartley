package com.dhemery.core;

public abstract class NullSafeUnaryOperator<T> implements SelfDescribingUnaryOperator<T> {
    @Override
    public T operate(T operand) {
        return operand == null ? defaultValue() : safelyOperate(operand);
    }

    protected T safelyOperate(T operand) { return operand; };
    protected T defaultValue() { return null; };
}
