package com.dhemery.configuring.options;

import com.dhemery.core.Maybe;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class Transaction implements SelfDescribing {
    private final String name;
    private final Maybe<String> value;

    public Transaction(String name, Maybe<String> value) {
        this.name = name;
        this.value = value;
    }

    public String name() { return name; }
    public Maybe<String> value() { return value; }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("[")
                .appendText(name)
                .appendText(":")
                .appendDescriptionOf(value)
                .appendText("]");
    }
}
