package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ValueConstraint extends TypeSafeMatcher<Option> {
    private final Matcher<? super String> criteria;

    public ValueConstraint(Matcher<? super String> criteria) {
        this.criteria = criteria;
    }

    @Override
    protected boolean matchesSafely(Option option) {
        return criteria.matches(option.value());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("value ").appendDescriptionOf(criteria);
    }
}
