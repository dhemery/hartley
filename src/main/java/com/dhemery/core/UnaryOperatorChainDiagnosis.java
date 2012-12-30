package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class UnaryOperatorChainDiagnosis<T> implements SelfDescribing {
    private final String name;
    private final T initialValue;
    private final Iterable<UnaryOperator<T>> operators;

    public UnaryOperatorChainDiagnosis(String name, T initialValue, Iterable<UnaryOperator<T>> operators) {
        this.initialValue = initialValue;
        this.operators = operators;
        this.name = name;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(operation(name, initialValue));
        T result = initialValue;
        for(UnaryOperator<T> operator : operators) {
            result = operator.operate(result);
            description.appendText(" -> ").appendDescriptionOf(operation(operator.toString(), result));
        }
    }

    private static <T> SelfDescribing operation(final String name, final T result) {
        return new SelfDescribing() {
            @Override
            public void describeTo(Description description) {
                description
                        .appendText("[")
                        .appendText(name)
                        .appendText(":")
                        .appendValue(result)
                        .appendText("]");
            }
        };
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
