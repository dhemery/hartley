package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.intoNewConfiguration;
import static com.dhemery.core.OperatorExpressions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

public class AnOptionsBackedConfiguration {
    private final Configuration configuration = intoNewConfiguration().build();

    @Test(expected=ConfigurationException.class)
    public void throwsIfARequiredOptionHasNoValue() {
        configuration.requiredOption("foo");
    }

    @Test
    public void describesRequiredOptionViolations() {
        try {
            configuration.requiredOption("foo");
        } catch (ConfigurationException violation) {
            assertThat(violation.getMessage(), endsWith("<[foo:null]>"));
        }
    }

    @Test
    public void describesEachOperationAndResultInRequiredOptionViolations() {
        configuration.define("foo", "   bar   ");
        try {
            configuration.requiredOption("foo", trimmed(), defaultingTo("monkey"), requiring(is("monkoo")));
        } catch (ConfigurationException violation) {
            assertThat(violation.getMessage(), endsWith("<[foo:\"   bar   \"] -> [trimmed:\"bar\"] -> [defaulting to \"monkey\":\"bar\"] -> [requiring (is \"monkoo\"):null]>"));
        }
    }
}
