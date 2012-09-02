package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AnEvaluatingPoller {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock public PollEvaluator evaluator;
    @Mock public Condition condition;
    @Mock public Ticker ticker;
    private Poller poller;

    @Before
    public void setup() {
        poller = new EvaluatingPoller(evaluator);
    }

    @Test
    public void succeedsIfEvaluationImmediatelyEvaluatesTrue() {
        context.checking(new Expectations(){{
            ignoring(ticker);
            allowing(evaluator).evaluate(with(condition));
                will(returnValue(true));
            never(evaluator).fail(with(any(Condition.class)));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void succeedsIfEvaluatorEvaluatesTrueBeforeTickerExpires() {
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            ignoring(ticker).tick();
            allowing(ticker).isExpired();
            will(onConsecutiveCalls(returnValue(false), returnValue(false), returnValue(true)));
            allowing(evaluator).evaluate(with(condition));
            will(onConsecutiveCalls(returnValue(false), returnValue(true)));
            never(evaluator).fail(with(any(Condition.class)));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void failsIfTickerExpiresBeforeEvaluatorEvaluatesTrue() {
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            ignoring(ticker).tick();
            allowing(ticker).isExpired();
                will(onConsecutiveCalls(returnValue(false), returnValue(false), returnValue(true)));
            allowing(evaluator).evaluate(with(condition));
                will(returnValue(false));
            oneOf(evaluator).fail(condition);
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void succeedsIfEvaluatorImmediatelyEvaluatesTrueEvenIfTickerIsAlreadyExpired() {
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            ignoring(ticker).tick();
            allowing(ticker).isExpired(); will(returnValue(true));
            allowing(evaluator).evaluate(with(condition));
                will(returnValue(true));
            never(evaluator).fail(condition);
        }});

        poller.poll(ticker, condition);
    }
}
