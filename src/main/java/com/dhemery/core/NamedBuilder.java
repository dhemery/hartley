package com.dhemery.core;

/**
 * A builder that describes itself with a fixed name.
 */
public abstract class NamedBuilder<T> extends Named implements SelfDescribingBuilder<T> {
    /**
     * Create a named builder.
     */
    public NamedBuilder(String name) {
		super(name);
	}
}
