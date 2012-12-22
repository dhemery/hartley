package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class Constrained<T> extends NullSafeUnaryOperator<T> {
    private final Matcher<? super T> constraint;

    public Constrained(Matcher<? super T> constraint) {
        this.constraint = constraint;
    }

    @Override
    protected T safelyOperate(T operand) {
        if(constraint.matches(operand)) return operand;
        return null;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("requiring ")
                .appendDescriptionOf(constraint);
    }
}
