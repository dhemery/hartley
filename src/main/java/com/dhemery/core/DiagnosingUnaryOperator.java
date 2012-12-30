package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class DiagnosingUnaryOperator<T> extends Named implements UnaryOperator<T> {
    private final UnaryOperator<T> operator;
    private final String prefix;
    private final Description description;

    public DiagnosingUnaryOperator(UnaryOperator<T> operator, String prefix, Description description) {
        super(operator.toString());
        this.operator = operator;
        this.prefix = prefix;
        this.description = description;
    }

    @Override
    public T operate(T operand) {
        T result = operator.operate(operand);
        description.appendText(prefix).appendDescriptionOf(operation(result));
        return result;
    }

    private SelfDescribing operation(T result) {
        return new Operation<T>(toString(), result);
    };

    private static class Operation<T> implements SelfDescribing {
        private final String name;
        private final T result;

        public Operation(String name, T result) {
            this.name = name;
            this.result = result;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendText("[")
                    .appendText(name)
                    .appendText(":")
                    .appendValue(result)
                    .appendText("]");
        }
    }
}
