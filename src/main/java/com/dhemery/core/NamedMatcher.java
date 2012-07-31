package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class NamedMatcher<T> extends TypeSafeMatcher<T> {
    private final String name;

    protected NamedMatcher(String name) {
        this.name = name;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(name);
    }
}
