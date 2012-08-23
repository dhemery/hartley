package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * An action that can describe itself.
 */

public interface SelfDescribingAction<T> extends SelfDescribing, Action<T> {
}
