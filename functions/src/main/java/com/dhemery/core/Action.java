package com.dhemery.core;

import org.hamcrest.SelfDescribing;

/**
 * An action that acts on a subject.
 * @param <S> the type of subject on which to act
 */
public interface Action<S> extends SelfDescribing {
    /**
     * Act on the subject.
     */
    void actOn(S subject);
}
