package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A builder that can describe itself.
 */
public interface SelfDescribingBuilder<T> extends SelfDescribing, Builder<T> {
}
