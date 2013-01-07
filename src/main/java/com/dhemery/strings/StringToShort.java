package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Short}.
 */
public class StringToShort extends NamedFeature<String, Short> {
    private static final Feature<String, Short> STRING_TO_SHORT = new StringToShort();

    public StringToShort() {
        super("to short");
    }

    @Override
    public Short of(String subject) {
        return Short.parseShort(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Short}.
     */
    public static Feature<String,Short> stringToShort() { return STRING_TO_SHORT; }
}
