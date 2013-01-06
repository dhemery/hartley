package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * An object that describes itself by a fixed name.
 */
public class Named implements SelfDescribing {
    private final String name;

    /**
     * Create an object with a fixed name.
     */
    public Named(String name) {
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
