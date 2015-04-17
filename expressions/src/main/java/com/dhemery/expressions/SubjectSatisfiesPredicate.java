package com.dhemery.expressions;

import java.util.function.Predicate;

public class SubjectSatisfiesPredicate<T> extends Descriptive implements Condition {
    private final T subject;
    private final Predicate<? super T> predicate;

    public SubjectSatisfiesPredicate(T subject, Predicate<? super T> predicate) {
        super(describedAs(subject, predicate));
        this.subject = subject;
        this.predicate = predicate;
    }

    @Override
    public boolean isSatisfied() {
        return predicate.test(subject);
    }
}
