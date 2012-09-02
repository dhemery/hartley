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
    @Mock public Ticker ticker;

    protected abstract Poller poller();

    @Test
    public void startsTheTickerBeforeEvaluatingTheCondition() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            oneOf(ticker).start(); inSequence(polling);
            allowing(condition).isSatisfied(); inSequence(polling); will(returnValue(true));
        }});

        poller().poll(ticker, condition);
    }
    @Test
    public void returnsWithoutThrowingIfConditionIsSatisfiedBeforeTickerExpires() {
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(onConsecutiveCalls(returnValue(false), returnValue(true)));
            allowing(ticker).isExpired(); will(onConsecutiveCalls(returnValue(false), returnValue(false), returnValue(true)));
            ignoring(ticker);
        }});

        poller().poll(ticker, condition);
    }

    @Test(expected = PollTimeoutException.class)
    public void throwsIfTickerExpiresBeforeConditionIsSatisfied() {
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(onConsecutiveCalls(returnValue(false), returnValue(false), returnValue(true)));
            allowing(ticker).isExpired(); will(onConsecutiveCalls(returnValue(false), returnValue(true)));
            ignoring(condition);
            ignoring(ticker);
        }});

        poller().poll(ticker, condition);
    }

    @Test
    public void ticksAfterEachDissatisfaction() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations() {{
            oneOf(condition).isSatisfied();
            inSequence(polling);
            will(returnValue(false));
            oneOf(ticker).tick();
            inSequence(polling);
            oneOf(condition).isSatisfied();
            inSequence(polling);
            will(returnValue(false));
            oneOf(ticker).tick();
            inSequence(polling);
            oneOf(condition).isSatisfied();
            inSequence(polling);
            will(returnValue(false));
            oneOf(ticker).tick();
            inSequence(polling);
            oneOf(condition).isSatisfied();
            inSequence(polling);
            will(returnValue(true));
            ignoring(ticker);
        }});

        poller().poll(ticker, condition);
    }

    @Test
    public void doesNotTickAfterConditionIsSatisfied() {
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            allowing(condition).isSatisfied(); will(returnValue(true));
            never(ticker).tick();
        }});

        poller().poll(ticker, condition);
    }
}
