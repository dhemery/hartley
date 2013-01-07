package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A supplier described by a fixed name.
 * @param <T> the type of object to supply
 */
public abstract class NamedSupplier<T> extends Named implements SelfDescribing, Supplier<T> {
    /**
     * Create a named supplier.
     */
	public NamedSupplier(String name) {
		super(name);
	}
}
