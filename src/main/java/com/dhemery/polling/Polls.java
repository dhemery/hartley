package com.dhemery.polling;

public class Polls {
    private Polls(){}

    public static PollBuilder newPoller() { return new PollBuilder(); }
}
