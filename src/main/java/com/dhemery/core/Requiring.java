package com.dhemery.core;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.hamcrest.Matchers.allOf;

/**
 * An operator that yields its operand if it satisfies some criteria.
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
        super("requiring " + StringDescription.asString(criteria));
        this.criteria = criteria;
    }

    @Override
    protected T operateOnNonNull(T operand) {
        if(criteria.matches(operand)) return operand;
        return null;
    }

    /**
     * Return an operator that yields its operand if it satisfies the criteria.
     */
    @Expression
    public static <T> UnaryOperator<T> requiring(Matcher<T>... criteria) {
        Matcher<T> criterion = criteria.length == 1 ? criteria[0] : (Matcher) allOf(criteria);
        return new Requiring<T>(criterion);
    }
}
