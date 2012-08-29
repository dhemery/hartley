package com.dhemery.polling;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.auto.Auto;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class AnEvaluatingPoller {
    Mockery context  = new Mockery();
    @Mock Ticker ticker;
    @Mock PollEvaluator evaluator;
    @Mock Condition condition;
    @Auto Sequence polling;
    Poller poller;

    @Before
    public void setup() {
        poller = new EvaluatingPoller(evaluator);
    }

    @Test
    public void returnsIfTheEvaluatorIsImmediatelySatisfied() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).isExpired(); will(returnValue(false));

            allowing(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(returnValue(true));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void returnsIfTheEvaluatorIsEventuallySatisfied() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).tick();
            allowing(ticker).isExpired(); will(returnValue(false));
            allowing(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(onConsecutiveCalls(returnValue(false), returnValue(true)));
        }});

        poller.poll(ticker, condition);
    }

    @Test(expected=PollTimeoutException.class)
    public void throwsIfTheTimerImmediatelyExpires() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).isExpired(); will(returnValue(true));
        }});

        poller.poll(ticker, condition);
    }

    @Test(expected=PollTimeoutException.class)
    public void throwsIfTheTimerEventuallyExpires() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).tick();
            allowing(evaluator).evaluate(with(condition), with(any(Long.class))); will(returnValue(false));
            allowing(ticker).isExpired(); will(onConsecutiveCalls(returnValue(false), returnValue(true)));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void reportsFailureCountWithEachEvaluation() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).tick();
            allowing(ticker).isExpired(); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 0); inSequence(polling); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 1); inSequence(polling); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 2); inSequence(polling); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 3); inSequence(polling); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 4); inSequence(polling); will(returnValue(false));
            oneOf(evaluator).evaluate(condition, 5); inSequence(polling); will(returnValue(true));
        }});

        poller.poll(ticker, condition);
    }
}
