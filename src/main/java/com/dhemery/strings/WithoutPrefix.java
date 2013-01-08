package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;

/**
 * Removes a prefix from a string.
 * Yields {@code null} if the operand is {@code null}.
 */
public class WithoutPrefix extends NullSafeUnaryOperator<String> {
    private final String prefix;

    public WithoutPrefix(String prefix) {
        super("without prefix " + Diagnostic.descriptionOf(prefix));
        this.prefix = prefix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.startsWith(prefix)) return operand.substring(prefix.length());
        return operand;
    }

    /**
     * Return an operator that removes a prefix from a string.
     */
    public static UnaryOperator<String> withoutPrefix(String prefix) {
        return new WithoutPrefix(prefix);
    }
}