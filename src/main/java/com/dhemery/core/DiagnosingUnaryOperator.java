package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

class DiagnosingUnaryOperator<T> implements UnaryOperator<T> {
    private final UnaryOperator<T> operator;
    private final String prefix;
    private final Description description;

    public DiagnosingUnaryOperator(UnaryOperator<T> operator, String prefix, Description description) {
        this.operator = operator;
        this.prefix = prefix;
        this.description = description;
    }

    @Override
    public T operate(T operand) {
        T result = operator.operate(operand);
        description.appendText(prefix).appendDescriptionOf(operation(operator, result));
        return result;
    }

    private SelfDescribing operation(UnaryOperator<T> operator, T result) {
        return new Operation<T>(operator, result);
    };

    private static class Operation<T> implements SelfDescribing {
        private final UnaryOperator<T> operator;
        private final T result;

        public Operation(UnaryOperator<T> operator, T result) {
            this.operator = operator;
            this.result = result;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendText("[")
                    .appendText(operator.toString())
                    .appendText(":")
                    .appendValue(result)
                    .appendText("]");
        }
    }
}
