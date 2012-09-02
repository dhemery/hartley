package com.dhemery.polling;

import com.dhemery.core.Condition;

public class SimplePoller implements Poller {
    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();

        do {
            if(condition.isSatisfied()) return;
            ticker.tick();
        } while(!ticker.isExpired());

        throw new PollTimeoutException(condition);
    }
}
