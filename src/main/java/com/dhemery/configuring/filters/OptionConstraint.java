package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OptionConstraint extends TypeSafeMatcher<Option> {
    private final Matcher<? super Option> criteria;

    public OptionConstraint(Matcher<? super Option> criteria) {
        this.criteria = criteria;
    }

    @Override
    protected boolean matchesSafely(Option option) {
        return criteria.matches(option);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("option ").appendDescriptionOf(criteria);
    }
}
