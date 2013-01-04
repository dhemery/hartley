package com.dhemery.core;

/**
 * An object that may be present.
 */
public interface MaybePresent {
    /**
     * Determine whether the object is present.
     */
    boolean isPresent();

    /**
     * A feature that determines whether its {@link MaybePresent} subject is present.
     */
    public class Present extends NamedFeature<MaybePresent, Boolean> {
        private static final Feature<MaybePresent, Boolean> PRESENT = new Present();

        private Present() {
            super("present");
        }

        @Override
        public Boolean of(MaybePresent subject) {
            return subject.isPresent();
        }

        /**
         * Create a feature that determines whether its {@link MaybePresent} subject is present.
         */
        public static Feature<MaybePresent,Boolean> present() {
            return PRESENT;
        }
    }
}
