package com.dhemery.expressing;


import com.dhemery.expressing.fixtures.ConstantCondition;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AnImmediateThe {
    @Test
    public void trueIfTheConditionIsSatisfied() {
        ConstantCondition condition = new ConstantCondition(true);
        assertThat(ImmediateExpressions.the(condition), is(true));
    }

    @Test
    public void falseIfTheConditionIsDissatisfied() {
        ConstantCondition condition = new ConstantCondition(false);
        assertThat(ImmediateExpressions.the(condition), is(false));
    }
}
