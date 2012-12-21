package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.configuring.options.OptionExpressions.*;
import static org.hamcrest.Matchers.*;

public class ConstraintFilters {
    @Test
    public void throwsIfNotDefined() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(option(is(defined()))));
    }

    @Test
    public void throwsIfNil() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(option(value(is(not(nullValue()))))));
    }

    @Test
    public void throwsJustBecause() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(option(is(defined())), option(value(is(allOf(nullValue(), not(nullValue())))))));
    }

    @Test
    public void performsTrimming() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.define("foo", "   bar   ");
        configuration.option("foo", require(option(is(defined()))), trimmed(), require(option(value(is(nullValue())))));
        configuration.option("foo");
    }
}
