package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Boolean}.
 */
public class StringToBoolean extends NamedFeature<String,Boolean> {
    private static final Feature<String, Boolean> STRING_TO_BOOLEAN = new StringToBoolean();

    public StringToBoolean() {
        super("to boolean");
    }

    @Override
    public Boolean of(String subject) {
        return Boolean.parseBoolean(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Boolean}.
     */
    public static Feature<String,Boolean> stringToBoolean() { return STRING_TO_BOOLEAN; }
}
