package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.Transformation;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns the option if the option satisfies some criteria.
 * Throws an exception if the option does not satisfy the criteria.
 */
public class Constraint implements Transformation<Option> {
    private final Matcher<? super Option> criteria;

    public Constraint(Matcher<? super Option> criteria) {
        this.criteria = criteria;
    }

    @Override
    public Option of(Option option) {
        assertThat(option, criteria);
        return new TransformedOption(option, this, option.value());
    }

    @Override
    public void describeTo(Description description) {
        criteria.describeTo(description);
    }
}
