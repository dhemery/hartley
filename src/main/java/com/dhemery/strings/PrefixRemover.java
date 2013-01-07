package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.expressing.Diagnostic;

public class PrefixRemover extends NullSafeUnaryOperator<String> {
    private final String prefix;

    public PrefixRemover(String prefix) {
        super("with prefix " + Diagnostic.descriptionOf(prefix));
        this.prefix = prefix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.startsWith(prefix)) return operand.substring(prefix.length());
        return operand;
    }
}