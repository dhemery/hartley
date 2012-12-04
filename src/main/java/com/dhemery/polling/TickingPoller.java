package com.dhemery.polling;

import com.dhemery.core.*;

/**
 * A poller that uses a ticker to delay between polls,
 * and throws a {@link PollTimeoutException} if the ticker expires before the condition is satisfied.
 */
public class TickingPoller extends ParameterizedPoller {
    public TickingPoller(Ticker ticker) {
        super(start(ticker), isNotExpired(ticker), tick(ticker), throwPollTimeoutException());
    }

    private static Action<Condition> start(final Ticker ticker) {
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

    private static Feature<Condition,Boolean> isNotExpired(final Ticker ticker) {
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
