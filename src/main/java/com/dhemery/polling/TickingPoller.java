package com.dhemery.polling;

import com.dhemery.core.*;

/**
 * A poller that uses a ticker to delay between polls,
 * and throws a {@link PollTimeoutException} if the ticker expires before the condition is satisfied.
 */
public class TickingPoller extends ComposedPoller {
    public TickingPoller(Ticker ticker) {
        super(startPolling(ticker), shouldContinuePolling(ticker), tick(ticker), throwPollTimeoutException());
    }

    private static Action<Condition> startPolling(final Ticker ticker) {
        return new NamedAction<Condition>("start") {
            @Override
            public void actOn(Condition object) {
                ticker.start();
            }
        };
    }

    private static Action<Condition> tick(final Ticker ticker) {
        return new NamedAction<Condition>("tick") {
            @Override
            public void actOn(Condition ignored) {
                ticker.tick();
            }
        };
    }

    private static Feature<Condition,Boolean> shouldContinuePolling(final Ticker ticker) {
        return new NamedFeature<Condition,Boolean>("continue polling") {
            @Override
            public Boolean of(Condition ignored) {
                return !ticker.isExpired();
            }
        };
    }

    private static Action<Condition> throwPollTimeoutException() {
        return new NamedAction<Condition>("poll failed") {
            @Override
            public void actOn(Condition condition) {
                throw new PollTimeoutException(condition);
            }
        };
    }
}
