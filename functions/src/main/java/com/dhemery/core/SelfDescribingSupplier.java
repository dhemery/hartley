package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A supplier that can describe itself.
 * @param <T> the type of object to supply
 */
public interface SelfDescribingSupplier<T> extends SelfDescribing, Supplier<T> {
}
