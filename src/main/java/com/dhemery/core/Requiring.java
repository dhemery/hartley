package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * An operator that returns its operand if it satisfies some criteria.
 * If the operand does not satisfy the criteria, the operator returns {@code null}.
 * @param <T> the type of operand
 */
public class Requiring<T> extends NullSafeUnaryOperator<T> {
    private final Matcher<? super T> criteria;

    /**
     * Create an operator that returns its operand if it satisfies some criteria.
     * @param criteria the criteria to satisfy
     */
    public Requiring(Matcher<? super T> criteria) {
        this.criteria = criteria;
    }

    @Override
    protected T operateOnNonNull(T operand) {
        if(criteria.matches(operand)) return operand;
        return null;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("requiring ")
                .appendDescriptionOf(criteria);
    }
}
