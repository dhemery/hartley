package com.dhemery.polling;

import org.junit.Test;

import static com.dhemery.polling.fixtures.Polling.Actions.doNothing;
import static com.dhemery.polling.fixtures.Polling.Conditions.satisfiedOnPollNumber;
import static com.dhemery.polling.fixtures.Polling.Timeframes.forever;

public class AComposedPoller extends PollerContract {
    @Override
    protected Poller pollerForContract() {
        return new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
    }

    @Test
    public void runsTheBeforePollingActionBeforePolling() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void pollsOnceBeforeAskingWhetherToContinue() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void asksWhetherToContinueAfterEachDissatisfaction() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void runsTheBetweenPollsActionAfterEachDissatisfactionIfPermittedToContinue() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void doesNotRunTheBetweenPollsActionIfRefusedPermissionToContinue() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void runsThePollFailureActionIfRefusedPermissionToContinue() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void stopsPollingIfRefusedPermissionToContinue() {
        Poller poller = new ComposedPoller(doNothing(), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }
}
