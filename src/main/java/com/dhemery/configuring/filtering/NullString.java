package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class NullString extends TypeSafeMatcher<Option> {
    @Override
    protected boolean matchesSafely(Option item) {
        return item.value() == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("null");
    }
}
