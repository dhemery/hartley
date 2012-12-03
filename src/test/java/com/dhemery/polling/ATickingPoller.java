package com.dhemery.polling;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

public class ATickingPoller extends PollerContract {
    private Poller poller;
    @Mock public Ticker ticker;

    @Before
    public void setup() {
        poller = new TickingPoller(ticker);
    }

    @Override
    protected Poller pollerForContract() {
        context.checking(new Expectations(){{
            ignoring(ticker);
        }});
        return new TickingPoller(ticker);
    }

    @Test
    public void startsTheTickerBeforeEvaluatingTheCondition() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            oneOf(ticker).start(); inSequence(polling);
            allowing(condition).isSatisfied(); inSequence(polling); will(returnValue(true));
        }});

        poller.poll(condition);
    }

    @Test
    public void ticksAfterEachDissatisfaction() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations() {{
            ignoring(ticker).start();
            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(true));
        }});

        poller.poll(condition);
    }

    @Test
    public void stopsTickingWhenTheConditionIsSatisfied() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            allowing(condition).isSatisfied(); inSequence(polling); will(returnValue(true));
            never(ticker).tick(); inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test(expected = Throwable.class)
    public void propagatesThrowableIfTheTickThrows() {
        context.checking(new Expectations(){{
            ignoring(ticker).start();
            allowing(condition).isSatisfied(); will(returnValue(false));
            allowing(ticker).tick(); will(throwException(new Throwable()));
        }});

        poller.poll(condition);
    }
}
