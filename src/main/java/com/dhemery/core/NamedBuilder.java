package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A builder described by a fixed name.
 * @param <T> the type of object to build
 */
public abstract class NamedBuilder<T> extends Named implements SelfDescribing, Builder<T> {
    /**
     * Create a named builder.
     */
    public NamedBuilder(String name) {
		super(name);
	}
}
