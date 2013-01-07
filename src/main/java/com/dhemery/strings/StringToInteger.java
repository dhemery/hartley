package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to an {@code Integer}.
 */
public class StringToInteger extends NamedFeature<String, Integer> {
    private static final Feature<String, Integer> STRING_TO_INTEGER = new StringToInteger();

    public StringToInteger() {
        super("to int");
    }

    @Override
    public Integer of(String subject) {
        return Integer.parseInt(subject);
    }

    /**
     * Return a feature that translates a string to an {@code Integer}.
     */
    public static Feature<String,Integer> stringToInteger() { return STRING_TO_INTEGER; }
}

