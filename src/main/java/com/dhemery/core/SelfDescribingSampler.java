package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A sampler that can describe itself.
 */
public interface SelfDescribingSampler<T> extends SelfDescribing, Sampler<T> {
}
