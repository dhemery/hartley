package com.dhemery.core;

/**
 * A builder that describes itself with a fixed name.
 * @param <T> the type of object to build
 */
public abstract class NamedBuilder<T> extends Named implements SelfDescribingBuilder<T> {
    /**
     * Create a named builder.
     */
    public NamedBuilder(String name) {
		super(name);
	}
}
