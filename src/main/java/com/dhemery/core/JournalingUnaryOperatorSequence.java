package com.dhemery.core;

import java.util.List;

/**
 * Wraps a list of operators and records their operations in a journal.
 * @param <T> the type of operand
 */
public class JournalingUnaryOperatorSequence<T> implements UnaryOperator<T> {
    private final List<UnaryOperator<T>> operators;
    private final Journal<T> journal;

    /**
     * Wrap the operators and record their operations in the journal.
     */
    public JournalingUnaryOperatorSequence(List<UnaryOperator<T>> operators, Journal<T> journal) {
        this.operators = operators;
        this.journal = journal;
    }

    @Override
    public T operate(T operand) {
        for(UnaryOperator<T> operator : operators) {
            operand = journaling(operator, journal).operate(operand);
        }
        return operand;
    }

    private UnaryOperator<T> journaling(final UnaryOperator<T> operator, final Journal<T> journal) {
        return new JournalingUnaryOperator<T>(operator, journal);
    }
}
