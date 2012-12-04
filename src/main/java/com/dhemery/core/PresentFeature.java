package com.dhemery.core;

/**
 * A feature that determines whether its {@link MaybePresent} subject is present.
 */
public class PresentFeature extends NamedFeature<MaybePresent, Boolean> {
    private PresentFeature() {
        super("present");
    }

    @Override
    public Boolean of(MaybePresent subject) {
        return subject.isPresent();
    }

    /**
     * Create a {@code PresentFeature}.
     */
    public static PresentFeature present() {
        return new PresentFeature();
    }
}
