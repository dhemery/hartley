package com.dhemery.expressing.fixtures;

import com.dhemery.core.Condition;
import org.hamcrest.Description;

public class ConstantCondition implements Condition {
    private final boolean satisfied;

    public ConstantCondition(boolean satisfied) {
        this.satisfied = satisfied;
    }

    @Override
    public boolean isSatisfied() {
        return satisfied;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("always ").appendValue(satisfied);
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        description.appendText("this condition is never satisfied");
    }
}
