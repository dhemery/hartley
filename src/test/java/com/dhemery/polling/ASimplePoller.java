package com.dhemery.polling;

public class ASimplePoller extends PollerContract {

    @Override
    protected Poller poller() {
        return new TickingPoller();
    }
}
