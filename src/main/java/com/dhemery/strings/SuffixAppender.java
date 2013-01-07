package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.expressing.Diagnostic;

public class SuffixAppender extends NullSafeUnaryOperator<String> {
    private final String suffix;

    public SuffixAppender(String suffix) {
        super("with suffix " + Diagnostic.descriptionOf(suffix));
        this.suffix = suffix;
    }

    @Override
    protected String operateOnNonNull(String operand) {
        if(operand.endsWith(suffix)) return operand;
        return operand + suffix;
    }
}
