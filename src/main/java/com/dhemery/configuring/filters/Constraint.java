package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Returns the option if the option satisfies some criteria.
 * Throws an exception if the option does not satisfy the criteria.
 */
public class Constraint implements Feature<Option,String> {
    private final Matcher<? super Option> criteria;

    public Constraint(Matcher<? super Option> optionCriteria) {
        criteria = optionCriteria;
    }

    @Override
    public String of(Option option) {
        assertThat("Problem with configuration option " + option.name(), option, criteria);
        return option.value();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("require(").appendDescriptionOf(criteria).appendText(")");
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
