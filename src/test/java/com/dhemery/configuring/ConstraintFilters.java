package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.configuring.OptionExpressions.defined;
import static com.dhemery.configuring.OptionExpressions.nil;
import static com.dhemery.configuring.OptionExpressions.require;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class ConstraintFilters {
    @Test
    public void throwsIfNotDefined() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(defined()));
    }

    @Test
    public void throwsIfNil() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(not(nil())));
    }

    @Test
    public void throwsJustBecause() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.option("foo", require(allOf(defined(), nil(), not(nil()))));
    }
}
