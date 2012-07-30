package com.dhemery.evaluating;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * A matcher that evaluates an object by evaluating a feature of the object against some criteria.
 * @param <T> the type of object to evaluate
 * @param <F> the type of feature to evaluate
 */
public class Has<T, F> extends FeatureMatcher<T, F> {
    private final Feature<T, F> feature;

	private Has(Feature<T, F> feature, Matcher<? super F> criteria) {
		super(criteria, "has " + StringDescription.toString(feature), feature.name());
        this.feature = feature;
    }

	@Override
	protected F featureValueOf(T object) {
		return feature.valueFor(object);
	}

    /**
     * Create a matcher that evaluates an object
     * by evaluating the given feature of the object
     * against the given criteria.
     */
	public static <T, F> Matcher<T> has(Feature<T, F> feature, Matcher<? super F> criteria) {
		return new Has<T, F>(feature, criteria);
	}
}
