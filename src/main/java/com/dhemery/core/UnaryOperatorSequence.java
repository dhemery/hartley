package com.dhemery.core;

import java.util.List;

public class UnaryOperatorSequence<T> implements UnaryOperator<T> {
    private final List<UnaryOperator<T>> operators;

    public UnaryOperatorSequence(List<UnaryOperator<T>> operators) {
        this.operators = operators;
    }

    @Override
    public T operate(T operand) {
        for(UnaryOperator<T> operator : operators) operand = operator.operate(operand);
        return operand;
    }
}
