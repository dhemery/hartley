package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class APollTimeoutException {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    private final String description = "My condition description";
    private final Condition condition = context.mock(Condition.class, description);

    @Test
    public void describesTheCondition() {
        context.checking(new Expectations(){{
            allowing(condition).explainDissatisfaction();
        }});
        Throwable exception = new PollTimeoutException(condition);
        String expectedMessage = "Timed out waiting until " + description;
        assertThat(exception.getMessage(), is(expectedMessage));
    }
}
