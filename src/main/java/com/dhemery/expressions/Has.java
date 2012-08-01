package com.dhemery.expressions;

import com.dhemery.core.Query;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A matcher that evaluates the current value of some feature of an object against some criteria.
 * @param <T> the type of object to evaluate
 * @param <V> the type of feature to evaluate
 */
public class Has<T, V> extends TypeSafeMatcher<T> {
    private final Query<T, V> query;
    private final Matcher<? super V> criteria;

    public Has(Query<T, V> query, Matcher<? super V> criteria) {
        this.query = query;
        this.criteria = criteria;
    }

    @Override
    protected boolean matchesSafely(T object) {
        return criteria.matches(query.query(object));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has ")
                .appendValue(query).appendText(" ")
                .appendDescriptionOf(criteria);
    }

    @Override
    protected void describeMismatchSafely(T subject, Description mismatchDescription) {
        mismatchDescription.appendText("had ")
                .appendValue(query).appendText(" ");
    }

    /**
     * Create a matcher that evaluates the current value of a feature of an object
     * against the given criteria.
     */
    public static <T, F> Matcher<T> has(Query<T, F> query, Matcher<? super F> criteria) {
        return new Has<T, F>(query, criteria);
    }
}
