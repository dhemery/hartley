package com.dhemery.polling;

import com.dhemery.core.Condition;

public interface Poller {
    void poll(Ticker ticker, Condition condition);
}
