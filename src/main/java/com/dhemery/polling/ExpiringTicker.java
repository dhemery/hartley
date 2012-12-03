package com.dhemery.polling;

/**
 * Wraps another ticker to perform an action if it is ticked after it expires.
 */
public class ExpiringTicker implements Ticker {
    private final Ticker ticker;
    private final Runnable action;

    /**
     * Wrap the given ticker to preform the action if it is ticked after it expires.
     */
    public ExpiringTicker(Ticker ticker, Runnable action) {
        this.ticker = ticker;
        this.action = action;
    }

    @Override
    public boolean isExpired() {
        return ticker.isExpired();
    }

    @Override
    public void start() {
        ticker.start();
    }

    @Override
    public void tick() {
        if(isExpired()) action.run();
        ticker.tick();
    }
}
