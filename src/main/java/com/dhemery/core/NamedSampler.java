package com.dhemery.core;

/**
 * A sampler described by a fixed name.
 * @param <V> the type of variable to sample
 */
public abstract class NamedSampler<V> extends Named implements Sampler<V> {
    /**
     * Create a named builder.
     */
    public NamedSampler(String name) {
		super(name);
	}
}
