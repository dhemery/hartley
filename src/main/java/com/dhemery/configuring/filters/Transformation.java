package com.dhemery.configuring.filters;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class Transformation implements SelfDescribing {
    private final String name;
    private final String value;

    public Transformation(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String name() {
        return name;
    }

    String value() {
        return value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("[").appendText(name).appendText(":").appendValue(value).appendText("]");
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
