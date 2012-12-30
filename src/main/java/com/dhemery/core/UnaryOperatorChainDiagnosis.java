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
        T result = diagnosing(identity(name), description).operate(initialValue);
        for(UnaryOperator<T> operator : operators) {
            result = diagnosing(operator, " -> ", description).operate(result);
        }
    }

    private UnaryOperator<T> diagnosing(UnaryOperator<T> operator, Description description) {
        return diagnosing(operator, "", description);
    }

    private UnaryOperator<T> diagnosing(UnaryOperator<T> operator, String prefix, Description description) {
        return new DiagnosingUnaryOperator<T>(operator, prefix, description);

    }

    private UnaryOperator<T> identity(String name) {
        return new IdentityOperator<T>(name);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }

}
