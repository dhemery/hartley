package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A builder that can describe itself.
 * @param <T> the type of object to build
 */
public interface SelfDescribingBuilder<T> extends SelfDescribing, Builder<T> {
}
