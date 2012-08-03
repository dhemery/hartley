package com.dhemery.polling;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Quietly evaluates whether a boolean value is true.
 * "Quietly" means that it does not describe itself.
 * This is primarily useful to provide a tacit matcher
 * for {@link Expressive} boolean features and samplers.
 */
class QuietlyTrue extends TypeSafeMatcher<Boolean> {
    /**
     * Report whether the value is true.
     */
    @Override
    protected boolean matchesSafely(Boolean value) {
        return value;
    }

    /**
     * Ignore the request for a description.
     */
    @Override
    public void describeTo(Description description) {}
}
