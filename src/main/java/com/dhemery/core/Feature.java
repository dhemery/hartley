package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A feature of an object.
 * @param <T> The type of object that has the feature
 * @param <V> The type of the feature
 */
public interface Feature<T, V>  extends SelfDescribing {
	/**
     * Return the current value of the feature for the object.
	 */
	V of(T object);
}
