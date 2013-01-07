package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Long}.
 */
public class StringToLong extends NamedFeature<String, Long> {
    private static final Feature<String, Long> STRING_TO_LONG = new StringToLong();

    public StringToLong() {
        super("to long");
    }

    @Override
    public Long of(String subject) {
        return Long.parseLong(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Long}.
     */
    public static Feature<String,Long> stringToLong() { return STRING_TO_LONG; }
}
