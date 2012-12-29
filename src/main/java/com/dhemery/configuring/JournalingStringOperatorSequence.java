package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;

import java.util.List;

public class JournalingStringOperatorSequence implements UnaryOperator<String> {
    private final List<UnaryOperator<String>> operators;
    private final Journal journal;

    public JournalingStringOperatorSequence(List<UnaryOperator<String>> operators, Journal journal) {
        this.operators = operators;
        this.journal = journal;
    }

    @Override
    public String operate(String operand) {
        for(UnaryOperator<String> operator : operators) {
            operand = operator.operate(operand);
            journal.record(operator.toString(), operand);

        }
        return operand;
    }
}
