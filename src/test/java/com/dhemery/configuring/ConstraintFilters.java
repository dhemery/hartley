package com.dhemery.configuring;

import org.junit.Ignore;
import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.core.OperatorExpressions.*;
import static org.hamcrest.Matchers.is;

public class ConstraintFilters {
    @Test
    @Ignore("Exists to demonstrate failure messages")
    public void throwsIfNil() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo", trimmed());
    }

    @Test
    @Ignore("Exists to demonstrate failure messages")
    public void throwsJustBecause() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.requiredOption("foo", defaultingTo("monkey"), requiring(is("monkoo")));
    }

    @Test
    @Ignore("Exists to demonstrate failure messages")
    public void performsTrimming() {
        Configuration configuration = intoNewConfiguration().build();
        configuration.define("foo", "   bar   ");
        configuration.requiredOption("foo", trimmed());
        configuration.requiredOption("foo", trimmed(), defaultingTo("monkey"), requiring(is("monkoo")));
    }
}
