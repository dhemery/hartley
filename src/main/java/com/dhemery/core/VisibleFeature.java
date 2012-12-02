package com.dhemery.core;

/**
 * A feature that determines whether the {@link MaybeVisible} subject is visible.
 */
public class VisibleFeature extends NamedFeature<MaybeVisible, Boolean> {
    private VisibleFeature() {
        super("visible");
    }

    @Override
    public Boolean of(MaybeVisible object) {
        return object.isVisible();
    }

    /**
     * Create a {@code VisibleFeature}.
     */
    public static VisibleFeature visible() {
        return new VisibleFeature();
    }
}
