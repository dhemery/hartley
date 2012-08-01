package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * Retrieves the current value of a feature from an object.
 * @param <T> The the type of object from which to retrieve the value
 * @param <V> The type of the feature
 */
public interface Query<T, V>  extends SelfDescribing{
	/**
     * Retrieve the current value of the feature from the object.
	 */
	V query(T object);
}
