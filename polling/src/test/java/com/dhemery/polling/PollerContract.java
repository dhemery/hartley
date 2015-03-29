package com.dhemery.polling;

import com.dhemery.polling.fixtures.Polling.Conditions.SatisfiedOnPollNumber;
import org.junit.Test;

import static com.dhemery.polling.fixtures.Polling.Conditions.satisfiedOnPollNumber;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class PollerContract {
    protected abstract Poller pollerForContract();

    @Test
    public void pollsUntilTheConditionIsSatisfied() {
        SatisfiedOnPollNumber condition = satisfiedOnPollNumber(3);
        pollerForContract().poll(condition);
        assertThat(condition.pollCount, is(3));
    }
}
