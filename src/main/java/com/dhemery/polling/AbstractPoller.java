package com.dhemery.polling;

import com.dhemery.core.Condition;

public abstract class AbstractPoller implements Poller {
    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();
        int pollCount = 0;
        while (!ticker.isExpired()) {
            if(check(condition, pollCount)) return;
            ticker.tick();
        }
        fail(condition);
    }

    protected abstract boolean check(Condition condition, int pollCount);
    protected abstract void fail(Condition condition);
}
