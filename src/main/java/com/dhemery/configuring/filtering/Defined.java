package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Defined extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option option) {
        return option.source().defines(option.name());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("defined");
    }

    @Override
    protected void describeMismatchSafely(Option option, Description mismatchDescription) {
        mismatchDescription
                .appendText("the configuration does not define required option ")
                .appendText(option.name());
    }
}
