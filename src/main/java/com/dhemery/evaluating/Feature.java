package com.dhemery.evaluating;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * A feature of an object.
 * @param <T> The type of object that has the feature
 * @param <F> The type of feature
 */
public abstract class Feature<T, F> implements SelfDescribing {
    private final String name;

    /**
     * Create a feature with a name.
     */
    protected Feature(String name) {
        this.name = name;
    }

    /**
     * The name of the feature.
     */
    public String name() {
        return name;
    }

	/**
     * Retrieve the value of the feature for the given object.
	 */
	public abstract F valueFor(T object);

    @Override
    public void describeTo(Description description) {
        description.appendText(name);
    }
}
