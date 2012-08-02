package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * Retrieves the current value of a feature from an object.
 * @param <T> The type of object that has the feature
 * @param <V> The type of the feature
 */
public interface Feature<T, V>  extends SelfDescribing {
	/**
     * Retrieve the current value of the feature from the object.
	 */
	V of(T object);
}
