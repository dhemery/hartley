package com.dhemery.core;

/**
 * A feature that determines whether the {@link MaybeVisibles} subject is visible.
 */
public class VisibleFeature extends NamedFeature<MaybeVisibles, Boolean> {
    private VisibleFeature() {
        super("visible");
    }

    @Override
    public Boolean of(MaybeVisibles object) {
        return object.isVisible();
    }

    /**
     * Create a {@code VisibleFeature}.
     */
    public static VisibleFeature visible() {
        return new VisibleFeature();
    }
}
