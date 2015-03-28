package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * Retrieves the value of a feature for a subject.
 * @param <S> The type of subject that has the feature
 * @param <F> The type of feature
 */
public interface Feature<S, F>  extends SelfDescribing {
	/**
     * Return the subject's current value for the feature.
	 */
	F of(S subject);
}
