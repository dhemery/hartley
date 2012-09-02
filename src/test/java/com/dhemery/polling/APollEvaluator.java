package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class APollEvaluator {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public Condition condition;

    protected abstract PollEvaluator evaluator();

    @Test
    public void evaluatesTrueIfTheConditionIsSatisfied() {
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(true));
        }});

        boolean result = evaluator().evaluate(condition);
        assertThat(result, is(true));
    }

    @Test
    public void evaluatesFalseIfTheConditionIsDissatisfied() {
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(false));
        }});

        boolean result = evaluator().evaluate(condition);
        assertThat(result, is(false));
    }

    @Test(expected = PollTimeoutException.class)
    public void failsByThrowingPollTimeoutException() {
        context.checking(new Expectations(){{
            ignoring(condition);
        }});

        evaluator().fail(condition);
    }
}
