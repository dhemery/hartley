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

    @Test
    public void startsTheTickerBeforePolling() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            oneOf(ticker).start(); inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(true)); inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void pollsBeforeCheckingForTickerExpiration() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            allowing(ticker).start();
            oneOf(condition).isSatisfied(); will(returnValue(true)); inSequence(polling);
            never(ticker).isExpired(); inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void ticksAfterEachDissatisfactionIfTickerIsNotExpired() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations() {{
            ignoring(ticker).start();
            allowing(ticker).isExpired(); will(returnValue(false));

            oneOf(condition).isSatisfied(); will(returnValue(false)); inSequence(polling);
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false)); inSequence(polling);
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false)); inSequence(polling);
            oneOf(ticker).tick(); inSequence(polling);
            oneOf(condition).isSatisfied();  will(returnValue(true)); inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void doesNotTickAfterSatisfaction() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(ticker).start();

            allowing(condition).isSatisfied(); will(returnValue(true)); inSequence(polling);
            never(ticker).tick(); inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test(expected = PollTimeoutException.class)
    public void throwsIfTickerExpiresBeforeConditionIsSatisfied() {
        context.checking(new Expectations(){{
            ignoring(condition).explainDissatisfaction();
            ignoring(ticker).start();

            allowing(condition).isSatisfied(); will(returnValue(false));
            allowing(ticker).isExpired(); will(returnValue(true));
        }});

        poller.poll(condition);
    }

    @Override
    protected Poller pollerForContract() {
        context.checking(new Expectations(){{
            ignoring(ticker);
        }});
        return poller;
    }
}
