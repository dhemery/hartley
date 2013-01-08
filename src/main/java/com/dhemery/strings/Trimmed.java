package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.core.UnaryOperator;

/**
 * Trims whitespace from the start and end of a string.
 * Yields {@code null} if the operand is {@code null}.
 */
public class Trimmed extends NullSafeUnaryOperator<String> {
    private static final Trimmed TRIMMED = new Trimmed();

    public Trimmed() {
        super("trimmed");
    }

    @Override
    protected String operateOnNonNull(String operand) {
        return operand.trim();
    }

    /**
     * Return an operator that trims whitespace from the start and end of a string.
     */
    public static UnaryOperator<String> trimmed() {
        return TRIMMED;
    }
}
