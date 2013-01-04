package com.dhemery.core;

/**
 * An object that may be visible.
 */
public interface MaybeVisible {
    /**
     * Determine whether the object is visible.
     */
    boolean isVisible();

    /**
     * A feature that determines whether its {@link MaybeVisible} subject is visible.
     */
    public class Visible extends NamedFeature<MaybeVisible, Boolean> {
        private static final Feature<MaybeVisible,Boolean> VISIBLE = new Visible();
        private Visible() {
            super("visible");
        }

        @Override
        public Boolean of(MaybeVisible subject) {
            return subject.isVisible();
        }

        /**
         * Create a {@code VisibleFeature}.
         */
        public static Feature<MaybeVisible,Boolean> visible() {
            return VISIBLE;
        }
    }
}
