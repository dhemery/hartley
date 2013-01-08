package com.dhemery.configuring;

import com.dhemery.core.Feature;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static com.dhemery.configuring.ConfigurationBuilder.newConfiguration;
import static com.dhemery.expressing.OperatorExpressions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

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
    public void appliesTheTranslator() {
        String optionName = "optionName";
        final String optionValue = "option value";
        final Integer translatedValue = 1984;

        configuration.define(optionName, optionValue);

        Mockery context = new Mockery();
        final Feature<String,Integer> translator = context.mock(Feature.class);
        context.checking(new Expectations() {{
            atLeast(1).of(translator).of(optionValue);
            will(returnValue(translatedValue));
        }});


        assertThat(configuration.option(translator, optionName), is(translatedValue));
        context.assertIsSatisfied();
    }
}
