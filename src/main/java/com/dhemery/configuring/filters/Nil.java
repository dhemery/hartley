package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Nil extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option option) {
        return option.value() == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("null");
    }
}
