package com.dhemery.expressing;

import com.dhemery.core.Condition;
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
        if(!condition.isSatisfied()) fail(condition);
    }

    private static void fail(Condition condition) {
        Description description = new StringDescription();
        description.appendText("Expected: ")
                .appendDescriptionOf(condition)
                .appendText(System.lineSeparator())
                .appendText("  but: ");
        condition.describeDissatisfactionTo(description);
        throw new AssertionError(description.toString());
    }
}
