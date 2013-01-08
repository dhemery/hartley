package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;

/**
 * Prepends a prefix to a string that lacks the prefix.
 * Yields {@code null} if the operand is {@code null}.
 */
public class WithPrefix extends NullSafeUnaryOperator<String> {
    private final String prefix;

    public WithPrefix(String prefix) {
        super("with prefix " + Diagnostic.descriptionOf(prefix));
        this.prefix = prefix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.startsWith(prefix)) return operand;
        return prefix + operand;
    }

    /**
     * Return an operator that prepends a prefix to a string that lacks the prefix.
     */
    public static UnaryOperator<String> withPrefix(String prefix) {
        return new WithPrefix(prefix);
    }
}
