package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class BaseOption implements Option {
    private final Options source;
    private final String name;

    public BaseOption(Options source, String name) {
        this.source = source;
        this.name = name;
    }

    @Override
    public Options source() {
        return source;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String value() {
        return source.option(name);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(name).appendText(":").appendValue(value());
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
