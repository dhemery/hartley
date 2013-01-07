package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;

/**
 * Trims whitespace from the start and end of a string.
 * Yields {@code null} if the operand is {@code null}.
 */
public class Trimmed extends NullSafeUnaryOperator<String> {
    public Trimmed() {
        super("trimmed");
    }

    @Override
    protected String operateOnNonNull(String operand) {
        return operand.trim();
    }
}
