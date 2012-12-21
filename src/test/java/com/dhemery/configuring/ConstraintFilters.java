package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.configuring.options.OptionExpressions.*;
import static org.hamcrest.Matchers.*;

public class ConstraintFilters {
    @Test
    public void throwsIfNil() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo", trimmed());
    }

    @Test
    public void throwsJustBecause() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo", requiring(value(), is(allOf(nullValue(), not(nullValue())))));
    }

    @Test
    public void performsTrimming() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.define("foo", "   bar   ");
        configuration.requiredOption("foo", trimmed());
        configuration.requiredOption("foo", trimmed(), requiring(is(nullValue())), defaultingTo("monkey"), requiring(is("monkoo")));
    }
}
