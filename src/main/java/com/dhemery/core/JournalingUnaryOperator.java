package com.dhemery.core;

/**
 * Wraps an operator and records its operations in a journal.
 * @param <T> the type of operand
 */
public class JournalingUnaryOperator<T> implements UnaryOperator<T> {
    private final UnaryOperator<T> operator;
    private final Journal<T> journal;

    /**
     * Wrap the operator and record its operations in the journal.
     */
    public JournalingUnaryOperator(UnaryOperator<T> operator, Journal<T> journal) {
        this.operator = operator;
        this.journal = journal;
    }

    @Override
    public T operate(T operand) {
        T result = operator.operate(operand);
        journal.record(operator.toString(), result);
        return result;
    }
}
