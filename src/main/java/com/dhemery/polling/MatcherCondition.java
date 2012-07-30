package com.dhemery.polling;

import org.hamcrest.Matcher;

public class MatcherCondition<S> implements Condition {
    private final S subject;
    private final Matcher<? super S> criteria;

    public MatcherCondition(S subject, Matcher<? super S> criteria) {
        this.subject = subject;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        return criteria.matches(subject);
    }
}
