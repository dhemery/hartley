package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns the option value if the option satisfies some constraint.
 * Throws an exception if the option does not satisfy the constraint.
 */
public class Constrained implements OptionFilter {
    private final Matcher<? super Option> constraint;

    public Constrained(Matcher<? super Option> constraint) {
        this.constraint = constraint;
    }

    @Override
    public String of(Option option) {
        assertThat(option, constraint);
        return option.value();
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(constraint);
    }
}
