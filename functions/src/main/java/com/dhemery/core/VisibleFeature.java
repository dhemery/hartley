package com.dhemery.core;

/**
 * A feature that determines whether its {@link MaybeVisible} subject is visible.
 */
public class VisibleFeature extends NamedFeature<MaybeVisible, Boolean> {
    private VisibleFeature() {
        super("visible");
    }

    @Override
    public Boolean of(MaybeVisible subject) {
        return subject.isVisible();
    }

    /**
     * Create a {@code VisibleFeature}.
     */
    public static VisibleFeature visible() {
        return new VisibleFeature();
    }
}
