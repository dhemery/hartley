package com.dhemery.configuring.options;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class Transaction implements SelfDescribing {
    private final String name;
    private final String value;

    public Transaction(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String name() { return name; }
    public String value() { return value; }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("[")
                .appendText(name)
                .appendText(":")
                .appendValue(value)
                .appendText("]");
    }
}
