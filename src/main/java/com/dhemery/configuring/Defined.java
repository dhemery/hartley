package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class Defined extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option option) {
        return option.configuration().defines(option.name());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("defines");
    }

    @Override
    protected void describeMismatchSafely(Option option, Description mismatchDescription) {
        mismatchDescription
                .appendText("the configuration does not define required option ")
                .appendText(option.name());
    }
}
