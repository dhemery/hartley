package com.dhemery.expressing;

import com.dhemery.expressing.fixtures.ConstantCondition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

import static com.dhemery.expressing.ImmediateExpressions.assertThat;
import static org.hamcrest.Matchers.is;

public class AnImmediateAssertion {
    @Test
    public void ifTheConditionIsSatisfied_returns() {
        assertThat(new ConstantCondition(true));
    }

    @Test
    public void ifTheConditionIsDissatisfied_throwsAnAssertionErrorThatDescribesTheDissatisfaction() {
        ConstantCondition condition = new ConstantCondition(false);
        Description expectedErrorMessage = new StringDescription();
        expectedErrorMessage.appendText("Expected: ")
                .appendDescriptionOf(condition)
                .appendText(System.lineSeparator())
                .appendText("  but: ");
        condition.describeDissatisfactionTo(expectedErrorMessage);
        try {
            assertThat(condition);
        } catch (AssertionError expectedError) {
            org.hamcrest.MatcherAssert.assertThat(expectedError.getMessage(), is(expectedErrorMessage.toString()));
        }
    }
}
