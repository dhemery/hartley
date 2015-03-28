package com.dhemery.polling.fixtures;

import com.dhemery.polling.events.ConditionDissatisfied;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class ConditionDissatisfiedMatcher extends TypeSafeMatcher<ConditionDissatisfied> {

    private final Matcher<String> description;
    private final Matcher<String> reason;
    private final Matcher<Integer> failureCount;

    private ConditionDissatisfiedMatcher(Matcher<String> description, Matcher<String> reason, Matcher<Integer> failureCount) {
        this.description = description;
        this.reason = reason;
        this.failureCount = failureCount;
    }

    @Override
    public boolean matchesSafely(ConditionDissatisfied event) {
        return description.matches(event.description())
                && reason.matches(event.reason())
                && failureCount.matches(event.failureCount());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a dissatisfaction event with description ").appendDescriptionOf(this.description)
                .appendText(" with reason ").appendDescriptionOf(reason)
                .appendText(" and failure count ").appendDescriptionOf(failureCount);
    }

    public static Matcher<ConditionDissatisfied> dissatisfaction(Matcher<String> description, Matcher<String> reason, Matcher<Integer> failureCount) {
        return new ConditionDissatisfiedMatcher(description, reason, failureCount);
    }

    public static Matcher<ConditionDissatisfied> dissatisfaction(String description, String reason, int failureCount) {
        return dissatisfaction(equalTo(description), equalTo(reason), equalTo(failureCount));
    }

    public static Matcher<ConditionDissatisfied> dissatisfaction(String description, String reason) {
        return dissatisfaction(equalTo(description), equalTo(reason), any(Integer.class));
    }

    public static Matcher<ConditionDissatisfied> dissatisfactionWithFailureCount(int failureCount) {
        return dissatisfaction(any(String.class), any(String.class), equalTo(failureCount));
    }
}
