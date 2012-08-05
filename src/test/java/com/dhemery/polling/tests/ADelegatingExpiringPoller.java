package com.dhemery.polling.tests;

import com.dhemery.polling.*;
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
public class ADelegatingExpiringPoller {
    Mockery context  = new Mockery();
    @Mock ExpiringTicker ticker;
    @Mock PollEvaluator evaluator;
    @Mock Condition condition;
    @Auto Sequence polling;
    Poller poller;

    @Before
    public void setup() {
        poller = new DelegatingExpiringPoller(ticker, evaluator);
    }

    @Test
    public void returnsIfTheEvaluatorIsImmediatelySatisfied() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).isExpired(); will(returnValue(false));

            oneOf(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(returnValue(true));

        }});

        poller.poll(condition);
    }

    @Test
    public void returnsIfTheEvaluatorIsEventuallySatisfied() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).tick();
            allowing(ticker).isExpired(); will(returnValue(false));
            oneOf(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(returnValue(false));
                inSequence(polling);
            oneOf(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(returnValue(true));
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test(expected=PollTimeoutException.class)
    public void throwsIfTheTimerImmediatelyExpires() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            oneOf(ticker).isExpired(); will(returnValue(true));
        }});

        poller.poll(condition);
    }

    @Test(expected=PollTimeoutException.class)
    public void throwsIfTheTimerEventuallyExpires() {
        context.checking(new Expectations() {{
            allowing(ticker).start();
            allowing(ticker).tick();
            allowing(evaluator).evaluate(with(condition), with(any(Long.class)));
                will(returnValue(false));
            oneOf(ticker).isExpired();
                inSequence(polling);
                will(returnValue(false));
            oneOf(ticker).isExpired();
                inSequence(polling);
                will(returnValue(true));
        }});

        poller.poll(condition);
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

        poller.poll(condition);
    }
}