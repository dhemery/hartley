package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Asserts that a condition is satisfied.
 */
public class ConditionAssert {
    private ConditionAssert(){}

    /**
     * Assert that the condition is satisfied.
     * @throws AssertionError if the condition is not satisfied
     */
    public static void assertThat(Condition condition) {
        if(condition.isSatisfied()) return;
        Description description = new StringDescription();
        description.appendText("\nExpected: ")
                .appendText(condition.description())
                .appendText("\n     but: ")
                .appendText(condition.failureDescription());
        throw new AssertionError(description.toString());
    }
}
