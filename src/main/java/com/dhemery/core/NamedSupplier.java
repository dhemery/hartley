package com.dhemery.core;

/**
 * A supplier that describes itself with a fixed name.
 */
public abstract class NamedSupplier<T> extends Named implements SelfDescribingSupplier<T> {
    /**
     * Create a named supplier.
     */
	public NamedSupplier(String name) {
		super(name);
	}
}
