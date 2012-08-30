package com.dhemery.core;

/**
 * A sampler that describes itself with a fixed name.
 */
public abstract class NamedSampler<T> extends Named implements Sampler<T> {
    /**
     * Create a named builder.
     */
    public NamedSampler(String name) {
		super(name);
	}
}
