package com.dhemery.fixtures;

import com.dhemery.polling.events.ConditionDissatisfied;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConditionDissatisfiedMatcher extends TypeSafeMatcher<ConditionDissatisfied> {
    private final String conditionDescription;
    private final String reason;
    private final int failureCount;

    private ConditionDissatisfiedMatcher(String conditionDescription, String reason, int failureCount) {
        this.conditionDescription = conditionDescription;
        this.reason = reason;
        this.failureCount = failureCount;
    }

    @Override
    public boolean matchesSafely(ConditionDissatisfied event) {
        boolean descriptionsMatch = event.description().equals(conditionDescription);
        boolean reasonsMatch = event.reason().equals(reason);
        boolean failureCountsMatch =  event.failureCount() == failureCount;
        return descriptionsMatch && reasonsMatch && failureCountsMatch;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a ConditionSatisfied event with description ").appendValue(conditionDescription)
                .appendText(" and reason ").appendValue(reason)
                .appendText(" and failure count ").appendValue(failureCount);
    }

    public static Matcher<ConditionDissatisfied> dissatisfaction(String description, String reason, int failureCount) {
        return new ConditionDissatisfiedMatcher(description, reason, failureCount);
    }
}
