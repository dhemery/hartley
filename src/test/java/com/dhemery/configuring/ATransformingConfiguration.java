package com.dhemery.configuring;

import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.newConfiguration;
import static com.dhemery.expressing.OperatorExpressions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ATransformingConfiguration {
    private final Configuration configuration = newConfiguration().named("Test Configuration").build();

    @Test(expected=ConfigurationException.class)
    public void throwsIfAnOptionHasNoValue() {
        configuration.option("foo");
    }

    @Test
    public void describesRequiredOptionViolations() {
        try {
            configuration.option("foo");
        } catch (ConfigurationException violation) {
            assertThat(violation.getMessage(), endsWith("<[foo:null]>"));
        }
    }

    @Test
    public void describesEachOperationAndResultInRequiredOptionViolations() {
        configuration.define("foo", "   bar   ");
        try {
            configuration.option("foo", trimmed(), defaultingTo("monkey"), requiring(is("monkoo")));
        } catch (ConfigurationException violation) {
            assertThat(violation.getMessage(), endsWith("<[foo:\"   bar   \"] -> [trimmed:\"bar\"] -> [defaulting to \"monkey\":\"bar\"] -> [requiring (is \"monkoo\"):null]>"));
        }
    }

    @Test
    public void yieldsOptionAsDesiredType() {
        assertThat(configuration.option("foo", Boolean.class, defaultingTo("true")), is(true));
        assertThat(configuration.option("foo", Byte.class, defaultingTo("3")), is(equalTo((byte)3)));
        assertThat(configuration.option("foo", Short.class, defaultingTo("3")), is(equalTo((short)3)));
        assertThat(configuration.option("foo", Integer.class, defaultingTo("3")), is(equalTo(3)));
        assertThat(configuration.option("foo", Long.class, defaultingTo("3")), is(equalTo(3L)));
        assertThat(configuration.option("foo", Float.class, defaultingTo("3.3")), is(equalTo(3.3f)));
        assertThat(configuration.option("foo", Double.class, defaultingTo("3.3")), is(equalTo(3.3d)));
        assertThat(configuration.option("foo", Character.class, defaultingTo("3")), is(equalTo('3')));
        assertThat(configuration.option("foo", String.class, defaultingTo("{a string}")), is("{a string}"));
    }
}
