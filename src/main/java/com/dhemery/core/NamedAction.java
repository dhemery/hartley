package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public abstract class NamedAction<T> implements Action<T>, SelfDescribing {
    private final String name;

    protected NamedAction(String name) {
        this.name = name;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
