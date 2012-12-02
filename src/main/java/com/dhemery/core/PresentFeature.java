package com.dhemery.core;

/**
 * A feature that determines whether a {@link MaybePresents} subject is present.
 */
public class PresentFeature extends NamedFeature<MaybePresents, Boolean> {
    private PresentFeature() {
        super("present");
    }

    @Override
    public Boolean of(MaybePresents subject) {
        return subject.isPresent();
    }

    /**
     * Create a {@code PresentFeature}.
     */
    public static PresentFeature present() {
        return new PresentFeature();
    }
}
