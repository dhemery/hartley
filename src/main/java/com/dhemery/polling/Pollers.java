package com.dhemery.polling;

public class Pollers {
    private Pollers(){}

    public static PollerBuilder newPoller() { return new PollerBuilder(); }
}
