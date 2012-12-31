package com.dhemery.core;

import java.util.List;

/**
 * An operator that applies a list of sub-operators in sequence.
 * This operator passes its operand to the first sub-operator,
 * then passes the result of each sub-operator as the operand of the next sub-operator.
 * This operator returns the result of the last sub-operator.
 * <p>If the list of sub-operators is empty, this operator yields its operand.
 * @param <T> the type of operand
 */
public class UnaryOperatorChain<T> implements UnaryOperator<T> {
    private final List<UnaryOperator<T>> operators;

    /**
     * Create an operator that applies the given operators in sequence.
     */
    public UnaryOperatorChain(List<UnaryOperator<T>> operators) {
        this.operators = operators;
    }

    @Override
    public T operate(T operand) {
        for(UnaryOperator<T> operator : operators) operand = operator.operate(operand);
        return operand;
    }
}
