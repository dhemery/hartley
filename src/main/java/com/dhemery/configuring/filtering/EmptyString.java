package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class EmptyString extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option item) {
        return item.value().isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("empty");
    }
}
