package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public abstract class NamedQuery<T,V> implements Query<T,V>, SelfDescribing {
    private final String name;

    protected NamedQuery(String name) {
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
