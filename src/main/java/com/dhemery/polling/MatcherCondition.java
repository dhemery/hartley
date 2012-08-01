package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * A condition that is satisfied when a subject satisfies some criteria.
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

    @Override
    public void describeTo(Description description) {
        description.appendValue(subject)
                .appendText(" ")
                .appendDescriptionOf(criteria);
    }

    @Override
    public void discribeDissatisfactionTo(Description description) {
        criteria.describeMismatch(subject, description);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
