package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * A feature that can describe itself.
 */
public interface SelfDescribingFeature<T,V> extends SelfDescribing, Feature<T,V> {
}
