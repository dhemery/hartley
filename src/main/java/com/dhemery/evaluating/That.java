package com.dhemery.evaluating;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Wraps another matcher and prepends "that " to its description.
 * @param <T> The type of object to evaluate
 */
public class That<T> extends TypeSafeMatcher<T> {
	private final Matcher<? super T> criteria;

    private That(Matcher<? super T> criteria) {
		this.criteria = criteria;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("that ").appendDescriptionOf(criteria);
	}

	@Override
	protected boolean matchesSafely(T item) {
		return criteria.matches(item);
	}

	/**
     * Create a matcher that wraps another matcher
     * and prepends "that " to its description.
	 */
    public static <S> That<S> that(Matcher<? super S> criteria) {
		return new That<S>(criteria);
	}
}
