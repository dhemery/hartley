package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Returns the option if the option satisfies all constraints.
 * Throws an exception if the option violates a constraint.
 */
public class Constraint<F> implements Feature<Option,String> {
    private final Feature<Option, F> feature;
    private final Matcher<? super F> constraint;

    public Constraint(Feature<Option,F> feature, Matcher<? super F> constraint) {
        this.feature = feature;
        this.constraint = constraint;
    }

    @Override
    public String of(Option option) {
        boolean featureMatches = constraint.matches(feature.of(option));
        return featureMatches ? option.value() : null;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("ensuring ")
                .appendDescriptionOf(feature)
                .appendText(" ")
                .appendDescriptionOf(constraint);
    }
}
