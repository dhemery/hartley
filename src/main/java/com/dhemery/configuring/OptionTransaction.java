package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class OptionTransaction implements SelfDescribing {
    private final String name;
    private final String value;

    public OptionTransaction(String name, String value) {
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

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
