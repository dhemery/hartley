package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A supplier that can describe itself.
 */
public interface SelfDescribingSupplier<T> extends SelfDescribing, Supplier<T> {
}
