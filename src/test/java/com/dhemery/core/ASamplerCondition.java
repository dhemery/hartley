package com.dhemery.core;

import com.dhemery.core.fixtures.DescribeMismatchAs;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.dhemery.core.SamplerCondition.sampleOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ASamplerCondition {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public Sampler<String> sampler;
    @Mock public Matcher<String> matcher;
    private final String sampledValue = "sampled value";

    @Before
    public void setup() {
        context.checking(new Expectations(){{
            atMost(1).of(sampler).takeSample();
            allowing(sampler).sampledValue(); will(returnValue(sampledValue));
        }});
    }

    @Test
    public void isSatisfiedIfTheMatcherMatchesTheSampledVariable(){
        context.checking(new Expectations(){{
            oneOf(matcher).matches(sampledValue); will(returnValue(true));
        }});

        assertThat(sampleOf(sampler, matcher).isSatisfied(), is(true));
    }

    @Test public void isDissatisfiedIfTheMatcherMismatchesTheSampledVariable(){
        context.checking(new Expectations(){{
            oneOf(matcher).matches(sampledValue); will(returnValue(false));
        }});

        assertThat(sampleOf(sampler, matcher).isSatisfied(), is(false));
    }

    @Test
    public void toString_describesTheMatcher() {
        Condition condition = sampleOf(sampler, matcher);
        String expectedDescription = "" + sampler + ' ' + matcher;
        assertThat(condition.toString(), is(expectedDescription));
    }

    @Test public void explainDissatisfaction_describesTheMismatchOfTheSampledValue(){
        final String mismatchDescription = "mismatch description";
        context.checking(new Expectations(){{
            oneOf(matcher).matches(sampledValue); will(returnValue(false));
            oneOf(matcher).describeMismatch(with(sampledValue), with(any(Description.class)));
                will(describeMismatchAs(mismatchDescription));
        }});

        Condition condition = sampleOf(sampler, matcher);
        condition.isSatisfied();

        assertThat(condition.explainDissatisfaction(), is(mismatchDescription));
    }

    private Action describeMismatchAs(final String mismatchDescription) {
        return new DescribeMismatchAs(mismatchDescription);
    }

}
