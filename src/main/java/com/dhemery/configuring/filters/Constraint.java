package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns the option if the option satisfies all constraints.
 * Throws an exception if the option violates a constraint.
 */
public class Constraint implements Feature<Option,String> {
    private final Matcher<? super Option> constraint;

    public Constraint(Matcher<? super Option> constraint) {
        this.constraint = constraint;
    }

    @Override
    public String of(Option option) {
        assertThat("Problem with configuration option " + option.name(), option, constraint);
        return option.value();
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("require(")
                .appendDescriptionOf(constraint)
                .appendText(")");
    }
}
