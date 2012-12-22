package com.dhemery.core;

import org.hamcrest.Description;

public class DefaultValue extends NullSafeUnaryOperator<String> {
    private final String defaultValue;

    public DefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    protected String defaultValue() {
        return defaultValue;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("defaulting to ")
                .appendValue(defaultValue);
    }
}
