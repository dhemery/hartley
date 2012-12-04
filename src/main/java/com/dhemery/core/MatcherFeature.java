package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * A feature that evaluates whether a subject matches a matcher.
 * @param <S> the type of subject
 */
public class MatcherFeature<S> implements Feature<S,Boolean> {
    private final Matcher<? super S> matcher;

    /**
     * Create a feature that evaluates whether a subject matches the matcher.
     */
    public MatcherFeature(Matcher<? super S> matcher) {
        this.matcher = matcher;
    }

    @Override
    public Boolean of(S subject) {
        return matcher.matches(subject);
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }
}
