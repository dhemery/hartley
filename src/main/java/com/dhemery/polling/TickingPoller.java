package com.dhemery.polling;

import com.dhemery.core.Condition;

import static com.dhemery.core.ActionRunner.run;
import static com.dhemery.polling.TickAction.tick;
import static com.dhemery.polling.Until.until;

/**
 * Repeatedly evaluate a condition until it is satisfied,
 * calling a ticker's {@code tick()} method between evaluations.
 */
public class TickingPoller implements Poller {
    private final Ticker ticker;

    /**
     * Create a poller that ticks the ticker between polls.
     */
    public TickingPoller(Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public void poll(Condition condition) {
        ticker.start();
        until(condition).repeat(run(ticker, tick()));
    }
}
