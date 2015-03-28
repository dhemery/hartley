package com.dhemery.core;

/**
 * A supplier that describes itself with a fixed name.
 * @param <T> the type of object to supply
 */
public abstract class NamedSupplier<T> extends Named implements SelfDescribingSupplier<T> {
    /**
     * Create a named supplier.
     */
	public NamedSupplier(String name) {
		super(name);
	}
}
