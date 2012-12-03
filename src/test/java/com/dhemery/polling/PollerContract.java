package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public abstract class PollerContract {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public Condition condition;

    protected abstract Poller pollerForContract();

    @Test
    public void evaluatesUntilTheConditionIsSatisfied() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            exactly(3).of(condition).isSatisfied(); will(returnValue(false)); inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(true)); inSequence(polling);
        }});

        pollerForContract().poll(condition);
    }

    @Test
    public void stopsEvaluatingWhenTheConditionIsSatisfied() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            oneOf(condition).isSatisfied(); will(returnValue(true)); inSequence(polling);
            never(condition).isSatisfied(); inSequence(polling);
        }});

        pollerForContract().poll(condition);
    }
}
