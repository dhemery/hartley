package com.dhemery.polling;

import com.dhemery.core.Action;
import com.dhemery.core.Condition;
import com.dhemery.core.Feature;

/**
 * A poller that performs the specified actions before polling, between polls, on poll failure,
 * and to determine whether to continue polling when the condition is not satisfied.
 */
public class ParameterizedPoller implements Poller {
    private final Action<Condition> betweenPolls;
    private final Action<Condition> beforePolling;
    private final Feature<Condition, Boolean> shouldContinuePolling;
    private final Action<Condition> onPollFailure;

    public ParameterizedPoller(Action<Condition> beforePolling, Feature<Condition,Boolean> shouldContinuePolling, Action<Condition> betweenPolls, Action<Condition> onPollFailure) {
        this.beforePolling = beforePolling;
        this.shouldContinuePolling = shouldContinuePolling;
        this.betweenPolls = betweenPolls;
        this.onPollFailure = onPollFailure;
    }

    @Override
    public void poll(Condition condition) {
        beforePolling.actOn(condition);
        while(!condition.isSatisfied()) {
            if(!shouldContinuePolling.of(condition)) {
                onPollFailure.actOn(condition);
                return;
            }
            betweenPolls.actOn(condition);
        }
    }
}
