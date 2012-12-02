package com.dhemery.polling;

import com.dhemery.core.Lazily;
import com.dhemery.core.Supplier;

public class ExpiringTick implements Runnable {
    private final Supplier<Ticker> runningTicker;
    private final RuntimeException expirationException;

    public ExpiringTick(Ticker ticker, RuntimeException expirationException) {
        this.expirationException = expirationException;
        runningTicker = Lazily.get(running(ticker));
    }

    private Supplier<Ticker> running(final Ticker ticker) {
        return new Supplier<Ticker>() {
            @Override
            public Ticker get() {
                ticker.start();
                return ticker;
            }
        };
    }

    @Override
    public void run() {
        Ticker ticker = runningTicker.get();
        ticker.tick();
        if(ticker.isExpired()) throw expirationException;
    }
}
