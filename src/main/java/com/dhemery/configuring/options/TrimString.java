package com.dhemery.configuring.options;

import com.dhemery.core.NullSafeUnaryOperator;
import org.hamcrest.Description;

class TrimString extends NullSafeUnaryOperator<String> {
    @Override
    protected String safelyOperate(String operand) {
        return operand.trim();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("trimmed");
    }
}
