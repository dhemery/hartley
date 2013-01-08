package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;
import com.dhemery.factory.Factory;

/**
 * Removes a suffix from a string.
 * Yields {@code null} if the operand is {@code null}.
 */
public class WithoutSuffix extends NullSafeUnaryOperator<String> {
    private final String suffix;

    public WithoutSuffix(String suffix) {
        super("without suffix " + Diagnostic.descriptionOf(suffix));
        this.suffix = suffix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.endsWith(suffix)) return operand.substring(0, operand.length()-suffix.length());
        return operand;
    }

    /**
     * Return an operator that removes a suffix from a string.
     */
    @Factory
    public static UnaryOperator<String> withoutSuffix(String suffix) {
        return new WithoutSuffix(suffix);
    }
}
