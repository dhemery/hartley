package com.dhemery.expressing;

import com.dhemery.factory.Factory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Quietly evaluates whether a boolean value is true.
 * "Quietly" means that the matcher does not describe itself.
 * This is primarily useful to provide a tacit matcher
 * for {@link Expressive} boolean features and samplers.
 */
public class QuietlyTrue extends TypeSafeMatcher<Boolean> {
    private static final Matcher<Boolean> QUIETLY_TRUE = new QuietlyTrue();

    /**
     * Report whether the value is true.
     */
    @Override
    public boolean matchesSafely(Boolean value) {
        return value;
    }

    /**
     * Ignore the request for a description.
     */
    @Override
    public void describeTo(Description description) {}

    /**
     * Create a matcher that matches a {@code true} value and does not describe itself.
     */
    public static Matcher<Boolean> isQuietlyTrue() {
        return QUIETLY_TRUE;
    }
}
