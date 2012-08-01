package com.dhemery.polling;

import org.hamcrest.Matcher;

/**
 * A condition that is satisfied when a subject satisfies the given criteria.
 * @param <S> the type of subject evaluated
 */
public class MatcherCondition<S> implements Condition {
    private final S subject;
    private final Matcher<? super S> criteria;

    /**
     * Create a condition that is satisfied when the subject satisfies the criteria.
     */
    public MatcherCondition(S subject, Matcher<? super S> criteria) {
        this.subject = subject;
        this.criteria = criteria;
    }

    /**
     * Return whether the subject satisfies the criteria.
     */
    @Override
    public boolean isSatisfied() {
        return criteria.matches(subject);
    }
}
