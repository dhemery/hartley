package com.dhemery.core;

/**
 * A feature that determines whether its {@link MaybeVisible} subject is visible.
 */
public class VisibleFeature extends NamedFeature<MaybeVisible, Boolean> {
    private static final Feature<MaybeVisible,Boolean> VISIBLE = new VisibleFeature();
    public VisibleFeature() {
        super("visible");
    }

    @Override
    public Boolean of(MaybeVisible subject) {
        return subject.isVisible();
    }

    /**
     * Create a feature that determines whether its {@link MaybeVisible} subject is visible.
     */
    @Expression
    public static Feature<MaybeVisible,Boolean> visible() {
        return VISIBLE;
    }
}
