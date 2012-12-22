package com.dhemery.core;

import java.util.List;

public class CompoundUnaryOperator<T> implements UnaryOperator<T> {
    private final List<UnaryOperator<T>> operators;

    public CompoundUnaryOperator(List<UnaryOperator<T>> operators) {
        this.operators = operators;
    }

    @Override
    public T operate(T operand) {
        for(UnaryOperator<T> operator : operators) operand = operator.operate(operand);
        return operand;
    }
}
