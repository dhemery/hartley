package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.configuring.options.OptionExpressions.*;
import static org.hamcrest.Matchers.*;

public class ConstraintFilters {
    @Test
    public void throwsIfNil() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo");
    }

    @Test
    public void throwsJustBecause() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo", ensuring(value(), is(allOf(nullValue(), not(nullValue())))));
    }

    @Test
    public void performsTrimming() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.define("foo", "   bar   ");
//        configuration.requiredOption("foo", trimmed(), ensuring(value(), is(nullValue())));
        configuration.requiredOption("foo", trimmed(), ensuring(value(), is(nullValue())), defaultingTo("monkey"), ensuring(is("monkoo")));
    }
}
