package com.dhemery.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Quietly evaluates whether a boolean value is true.
 * "Quietly" means that it does not describe itself.
 * This is primarily useful to provide a tacit matcher
 * for {@link com.dhemery.polling.Expressive} boolean features and samplers.
 */
public class QuietlyTrue extends TypeSafeMatcher<Boolean> {
    private static final Matcher<Boolean> quietlyTrue = new QuietlyTrue();

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

    public static Matcher<Boolean> isQuietlyTrue() {
        return quietlyTrue;
    }
}
