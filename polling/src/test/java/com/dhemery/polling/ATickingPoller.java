package com.dhemery.polling;

import org.junit.Test;

import static com.dhemery.polling.fixtures.Polling.Actions.doNothing;
import static com.dhemery.polling.fixtures.Polling.Actions.log;
import static com.dhemery.polling.fixtures.Polling.Conditions.satisfiedOnPollNumber;
import static com.dhemery.polling.fixtures.Polling.Timeframes.forever;

public class ATickingPoller extends PollerContract {
    @Test
    public void startsTheTickerBeforePolling() {
        Poller poller = new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void pollsBeforeCheckingForTickerExpiration() {
        Poller poller = new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void ticksAfterEachDissatisfactionIfTickerIsNotExpired() {
        Poller poller = new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test
    public void doesNotTickAfterSatisfaction() {
        Poller poller = new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Test(expected = PollTimeoutException.class)
    public void throwsIfTickerExpiresBeforeConditionIsSatisfied() {
        Poller poller = new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
        poller.poll(satisfiedOnPollNumber(1));
    }

    @Override
    protected Poller pollerForContract() {
        return new ComposedPoller(log("before polling"), forever(), doNothing(), doNothing());
    }
}
