package com.dhemery.polling;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

public class AParameterizedPoller extends PollerContract {
    private Poller poller;
    @Mock public Action<Condition> beforePolling;
    @Mock public Feature<Condition, Boolean> shouldContinuePolling;
    @Mock public Action<Condition> betweenPolls;
    @Mock public Action<Condition> onPollFailure;

    @Override
    protected Poller pollerForContract() {
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            allowing(shouldContinuePolling).of(condition); will(returnValue(true));
            ignoring(betweenPolls);
            ignoring(onPollFailure);
        }});
        return poller;
    }

    @Before
    public void setup() {
        poller = new ParameterizedPoller(beforePolling, shouldContinuePolling, betweenPolls, onPollFailure);
    }

    @Test
    public void runsTheBeforePollingActionBeforePolling() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            oneOf(beforePolling).actOn(condition);
                inSequence(polling);
            oneOf(condition).isSatisfied();  will(returnValue(true));
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void pollsOnceBeforeAskingWhetherToContinue() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);

            oneOf(condition).isSatisfied();  will(returnValue(true));
                inSequence(polling);
            never(shouldContinuePolling).of(condition);
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void asksWhetherToContinueAfterEachDissatisfaction() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            ignoring(betweenPolls);

            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(shouldContinuePolling).of(condition);  will(returnValue(true));
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(shouldContinuePolling).of(condition); will(returnValue(true));
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(shouldContinuePolling).of(condition); will(returnValue(true));
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(true));
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void runsTheBetweenPollsActionAfterEachDissatisfactionIfPermittedToContinue() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            allowing(shouldContinuePolling).of(condition); will(returnValue(true));

            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(betweenPolls).actOn(condition);;
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(betweenPolls).actOn(condition);
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(betweenPolls).actOn(condition);
                inSequence(polling);
            oneOf(condition).isSatisfied(); will(returnValue(true));
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void doesNotRunTheBetweenPollsActionIfRefusedPermissionToContinue() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            ignoring(onPollFailure);
            allowing(condition).isSatisfied(); will(returnValue(false));

            oneOf(shouldContinuePolling).of(condition); will(returnValue(false));
                inSequence(polling);
            never(betweenPolls).actOn(condition);
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void runsThePollFailureActionIfRefusedPermissionToContinue() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            allowing(condition).isSatisfied(); will(returnValue(false));

            oneOf(shouldContinuePolling).of(condition); will(returnValue(false));
                inSequence(polling);
            oneOf(onPollFailure).actOn(condition);
                inSequence(polling);
        }});

        poller.poll(condition);
    }

    @Test
    public void stopsPollingIfRefusedPermissionToContinue() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(beforePolling);
            ignoring(onPollFailure);

            oneOf(condition).isSatisfied(); will(returnValue(false));
                inSequence(polling);
            oneOf(shouldContinuePolling).of(condition); will(returnValue(false));
                inSequence(polling);
            never(condition).isSatisfied();
                inSequence(polling);
        }});

        poller.poll(condition);
    }
}
