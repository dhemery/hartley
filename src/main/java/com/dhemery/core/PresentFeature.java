package com.dhemery.core;

import com.dhemery.factory.Factory;

/**
 * A feature that determines whether its {@link MaybePresent} subject is present.
 */
public class PresentFeature extends NamedFeature<MaybePresent, Boolean> {
    private static final Feature<MaybePresent, Boolean> PRESENT = new PresentFeature();

    public PresentFeature() {
        super("present");
    }

    @Override
    public Boolean of(MaybePresent subject) {
        return subject.isPresent();
    }

    /**
     * Create a feature that determines whether its {@link MaybePresent} subject is present.
     */
    @Factory
    public static Feature<MaybePresent,Boolean> present() {
        return PRESENT;
    }
}
