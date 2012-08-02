package com.dhemery.polling;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class ConditionAssert {
    public static void assertThat(Condition condition) {
        if(condition.isSatisfied()) return;
        Description description = new StringDescription();
        description.appendText("\nExpected: ")
                    .appendDescriptionOf(condition)
                    .appendText("\n     but: ");
        condition.describeDissatisfactionTo(description);
        throw new AssertionError(description.toString());
    }
}
