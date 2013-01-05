package com.dhemery.core;

/**
 * An operator that trims its string operand.
 */
public class TrimString extends NullSafeUnaryOperator<String> {
    public TrimString() {
        super("trimmed");
    }

    @Override
    protected String operateOnNonNull(String operand) {
        return operand.trim();
    }
}
