package com.dhemery.core;

/**
 * An action that acts on a subject.
 * @param <S> the type of subject on which to act
 */
public interface Action<S> {
    /**
     * Act on the subject.
     */
    void actOn(S subject);
}
