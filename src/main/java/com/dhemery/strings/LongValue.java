package com.dhemery.strings;

import com.dhemery.core.Expression;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Long}.
 */
public class LongValue extends NamedFeature<String, Long> {
    private static final LongValue LONG_VALUE = new LongValue();

    public LongValue() {
        super("Long value");
    }

    @Override
    public Long of(String subject) {
        return Long.parseLong(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Long}.
     */
    @Expression
    public static Feature<String, Long> longValue() {
        return LONG_VALUE;
    }
}
