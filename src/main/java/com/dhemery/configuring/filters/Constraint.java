package com.dhemery.configuring.filters;

import com.dhemery.configuring.ConfigurationException;
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
        try {
            assertThat(option, criteria);
        } catch (AssertionError cause) {
            Description description = new StringDescription();
            description.appendText("Problem with configuration option ")
                    .appendText(option.name())
                    .appendText(":")
                    .appendText(cause.getMessage());
            ConfigurationException exception = new ConfigurationException(description.toString());
            exception.setStackTrace(cause.getStackTrace());
            throw exception;
        }
        return option.value();
    }

    @Override
    public void describeTo(Description description) {
        criteria.describeTo(description);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
