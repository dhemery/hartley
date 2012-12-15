package com.dhemery.configuring;

import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns the option value if the option satisfies some criteria.
 * Throws an exception if the option does not satisfy the criteria.
 */
public class Constraint implements OptionFilter {
    private final Matcher<? super Option> criteria;

    public Constraint(Matcher<? super Option> criteria) {
        this.criteria = criteria;
    }

    @Override
    public String valueOf(Option option) {
        assertThat(option, criteria);
        return option.value();
    }
}
