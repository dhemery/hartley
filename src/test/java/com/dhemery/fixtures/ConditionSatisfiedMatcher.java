package com.dhemery.fixtures;

import com.dhemery.polling.events.ConditionSatisfied;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConditionSatisfiedMatcher extends TypeSafeMatcher<ConditionSatisfied> {
    private final String conditionDescription;
    private final int failureCount;

    private ConditionSatisfiedMatcher(String conditionDescription, int failureCount) {
        this.conditionDescription = conditionDescription;
        this.failureCount = failureCount;
    }

    @Override
    protected boolean matchesSafely(ConditionSatisfied event) {
        boolean descriptionsMatch = event.description().equals(conditionDescription);
        boolean failureCountsMatch =  event.failureCount() == failureCount;
        return descriptionsMatch && failureCountsMatch;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a ConditionSatisfied event with description ").appendValue(conditionDescription)
                .appendText(" and failure count ")
                .appendValue(failureCount);
    }

    public static Matcher<ConditionSatisfied> satisfaction(String description, int failureCount) {
        return new ConditionSatisfiedMatcher(description, failureCount);
    }
}
