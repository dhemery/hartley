package com.dhemery.core;

import org.hamcrest.Description;

import java.util.List;

public class DiagnosingUnaryOperatorChain<T> extends Named implements UnaryOperator<T> {
    private final UnaryOperator<T> identity;
    private final Description description;
    private final List<UnaryOperator<T>> operators;

    public DiagnosingUnaryOperatorChain(String name, List<UnaryOperator<T>> operators, Description description) {
        super(name);
        this.operators = operators;
        this.description = description;
        identity = identity(name);
    }

    @Override
    public T operate(T operand) {
        T value = diagnosing(identity).operate(operand);
        for(UnaryOperator<T> operator : operators) {
            value = diagnosing(operator, " -> ").operate(value);
        }
        return value;
    }

    private IdentityOperator<T> identity(String name) {
        return new IdentityOperator<T>(name);
    }

    private UnaryOperator<T> diagnosing(UnaryOperator<T> operator) {
        return diagnosing(operator, "");
    }

    private UnaryOperator<T> diagnosing(UnaryOperator<T> operator, String prefix) {
        return new DiagnosingUnaryOperator<T>(operator, prefix, description);
    }
}
