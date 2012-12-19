package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class Option implements SelfDescribing {
    private final String value;
    private final Options source;
    private final String name;

    public Option(Options source, String name, String value) {
        this.source = source;
        this.name = name;
        this.value = value;
    }

    public String name() { return name; }
    public Options source() { return source; }
    public String value() { return value; }

    @Override
    public void describeTo(Description description) {
        description.appendText(value);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
