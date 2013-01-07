package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.expressing.Diagnostic;

public class PrefixPrepender extends NullSafeUnaryOperator<String> {
    private final String prefix;

    public PrefixPrepender(String prefix) {
        super("with prefix " + Diagnostic.descriptionOf(prefix));
        this.prefix = prefix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.startsWith(prefix)) return operand;
        return prefix + operand;
    }
}