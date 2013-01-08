package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import com.dhemery.factory.Factory;

/**
 * Translates a string to an {@code Integer}.
 */
public class IntegerValue extends NamedFeature<String, Integer> {
    private static final IntegerValue INTEGER_VALUE = new IntegerValue();

    public IntegerValue() {
        super("Integer value");
    }

    @Override
    public Integer of(String subject) {
        return Integer.parseInt(subject);
    }

    /**
     * Return a feature that translates a string to an {@code Integer}.
     */
    @Factory
    public static Feature<String, Integer> integerValue() {
        return INTEGER_VALUE;
    }
}
