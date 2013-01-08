package com.dhemery.strings;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.core.UnaryOperator;
import com.dhemery.factory.Factory;

/**
 * Appends a suffix to a string that lacks the suffix.
 * Yields {@code null} if the operand is {@code null}.
 */
public class WithSuffix extends NullSafeUnaryOperator<String> {
    private final String suffix;

    public WithSuffix(String suffix) {
        super("with suffix " + Diagnostic.descriptionOf(suffix));
        this.suffix = suffix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.endsWith(suffix)) return operand;
        return operand + suffix;
    }

    /**
     * Return an operator that appends a suffix to a string that lacks the suffix.
     */
    @Factory
    public static UnaryOperator<String> withSuffix(String suffix) {
        return new WithSuffix(suffix);
    }
}
