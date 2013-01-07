package com.dhemery.strings;

import com.dhemery.core.NullSafeUnaryOperator;
import com.dhemery.expressing.Diagnostic;

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
}
