package com.dhemery.polling;

public class ExpiringPoller extends TickingPoller {
    public ExpiringPoller(Ticker ticker, Runnable onExpiration) {
        super(new ExpiringTicker(ticker, onExpiration));
    }
}
