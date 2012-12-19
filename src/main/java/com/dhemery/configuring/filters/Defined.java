package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Defined extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option option) {
        return option.source().names().contains(option.name());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("defined");
    }
}
