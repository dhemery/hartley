package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * A feature that translates its string subject to a long.
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
     * Return a feature that translates its string subject to a long.
     */
    public static Feature<String,Long> stringToLong() { return STRING_TO_LONG; }
}
