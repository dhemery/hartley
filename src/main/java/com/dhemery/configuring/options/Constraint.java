package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import com.dhemery.core.Maybe;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.dhemery.core.Maybe.absent;

/**
 * Returns the option if the option satisfies all constraints.
 * Throws an exception if the option violates a constraint.
 */
public class Constraint<F> implements Feature<Option,Maybe<String>> {
    private final Feature<Option, F> feature;
    private final Matcher<? super F> constraint;

    public Constraint(Feature<Option,F> feature, Matcher<? super F> constraint) {
        this.feature = feature;
        this.constraint = constraint;
    }

    @Override
    public Maybe<String> of(Option option) {
        boolean featureMatches = constraint.matches(feature.of(option));
        if(featureMatches) return option.value();
        return absent();
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("requiring ")
                .appendDescriptionOf(feature)
                .appendText(" ")
                .appendDescriptionOf(constraint);
    }
}
