package com.dhemery.core;

import org.hamcrest.Description;

/**
 * An operator that trims its string operand.
 */
public class TrimString extends NullSafeUnaryOperator<String> {
    @Override
    protected String safelyOperate(String operand) {
        return operand.trim();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("trimmed");
    }
}
