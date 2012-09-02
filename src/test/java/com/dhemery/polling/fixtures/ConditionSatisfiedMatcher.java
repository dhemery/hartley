package com.dhemery.polling.fixtures;

import com.dhemery.polling.events.ConditionSatisfied;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class ConditionSatisfiedMatcher extends TypeSafeMatcher<ConditionSatisfied> {

    private final Matcher<String> description;
    private final Matcher<Integer> failureCount;

    private ConditionSatisfiedMatcher(Matcher<String> description, Matcher<Integer> failureCount) {
        this.description = description;
        this.failureCount = failureCount;
    }

    @Override
    protected boolean matchesSafely(ConditionSatisfied event) {
        return description.matches(event.description())
                && failureCount.matches(event.failureCount());
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("a satisfaction event with description ").appendDescriptionOf(this.description)
                .appendText(" with failure count ").appendDescriptionOf(failureCount);
    }


    public static Matcher<ConditionSatisfied> satisfaction(String description) {
        return new ConditionSatisfiedMatcher(equalTo(description), any(Integer.class));
    }

    public static Matcher<ConditionSatisfied> satisfaction(String description, int failureCount) {
        return new ConditionSatisfiedMatcher(equalTo(description), equalTo(failureCount));
    }
}
