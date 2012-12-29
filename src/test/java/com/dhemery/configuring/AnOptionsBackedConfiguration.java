package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.newConfiguration;
import static com.dhemery.core.OperatorExpressions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AnOptionsBackedConfiguration {
    private final Configuration configuration = newConfiguration().withName("Test Configuration").build();

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

    @Test
    public void yieldsOptionAsDesiredType() {
        assertThat(configuration.requiredOption("foo", Boolean.class, defaultingTo("true")), is(true));
        assertThat(configuration.requiredOption("foo", Byte.class, defaultingTo("3")), is(equalTo((byte)3)));
        assertThat(configuration.requiredOption("foo", Short.class, defaultingTo("3")), is(equalTo((short)3)));
        assertThat(configuration.requiredOption("foo", Integer.class, defaultingTo("3")), is(equalTo(3)));
        assertThat(configuration.requiredOption("foo", Long.class, defaultingTo("3")), is(equalTo(3L)));
        assertThat(configuration.requiredOption("foo", Float.class, defaultingTo("3.3")), is(equalTo(3.3f)));
        assertThat(configuration.requiredOption("foo", Double.class, defaultingTo("3.3")), is(equalTo(3.3d)));
        assertThat(configuration.requiredOption("foo", Character.class, defaultingTo("3")), is(equalTo('3')));
        assertThat(configuration.requiredOption("foo", String.class, defaultingTo("{a string}")), is("{a string}"));
    }
}
