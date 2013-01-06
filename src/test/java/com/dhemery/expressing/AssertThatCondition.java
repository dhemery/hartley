package com.dhemery.expressing;

import com.dhemery.core.Condition;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AssertThatCondition {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public Condition condition;

    @Test
    public void doesNotThrowIfTheConditionIsSatisfied() {
        context.checking(new Expectations(){{
            oneOf(condition).isSatisfied(); will(returnValue(true));
        }});
        Evaluations.assertThat(condition);
    }

    @Test(expected=AssertionError.class)
    public void throwsIfTheConditionIsNotSatisfied() {
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(false));
            allowing(condition).explainDissatisfaction();
        }});
        Evaluations.assertThat(condition);
    }

    @Test
    public void failureDescribesTheContextAndConditionAndExplanation() {
        String assertionContext = "My assertion context";
        final String explanation = "My condition explanation";
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(false));
            allowing(condition).explainDissatisfaction(); will(returnValue(explanation));
        }});
        String expectedMessage = assertionContext + "\nExpected: " + condition + "\n     but: " + explanation;
        try {
            Evaluations.assertThat(assertionContext, condition);
        } catch (AssertionError thrown) {
            assertThat(thrown.getMessage(), is(expectedMessage));
        }
    }
}
