package com.dhemery.expressing;

import com.dhemery.expressing.fixtures.ConstantCondition;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AConditionAssert {
    @Test
    public void ifTheConditionIsSatisfied_returns() {
        ConditionAssert.assertThat(new ConstantCondition(true));
    }

    @Test
    public void ifTheConditionIsDissatisfied_throwsAnAssertionErrorThatDescribesTheDissatisfaction() {
        ConstantCondition condition = new ConstantCondition(false);

        try {
            ConditionAssert.assertThat(condition);
            Assert.fail("the immediate assertion did not throw the expected error");
        } catch (AssertionError thrown) {
            assertThat(thrown.getMessage(), is(expectedErrorMessageFor(condition)));
        }
    }

    private String expectedErrorMessageFor(ConstantCondition condition) {
        Description expectedErrorMessage = new StringDescription();
        expectedErrorMessage.appendText("Expected: ")
                .appendDescriptionOf(condition)
                .appendText(System.lineSeparator())
                .appendText("  but: ");
        condition.describeDissatisfactionTo(expectedErrorMessage);
        return expectedErrorMessage.toString();
    }
}
