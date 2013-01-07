package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;

/**
 * An operator that trims its string operand.
 */
public class StringTrimmer extends NullSafeUnaryOperator<String> {
    public StringTrimmer() {
        super("trimmed");
    }

    @Override
    protected String operateOnNonNull(String operand) {
        return operand.trim();
    }
}
