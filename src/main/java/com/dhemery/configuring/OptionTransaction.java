package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

class OptionTransaction<T> implements SelfDescribing {
    public final String name;
    public final T value;

    public OptionTransaction(String name, T value) {
        this.name = name;
        this.value = value;
    }

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
