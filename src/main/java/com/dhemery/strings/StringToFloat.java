package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Float}.
 */
public class StringToFloat extends NamedFeature<String, Float> {
    private static final Feature<String, Float> STRING_TO_FLOAT = new StringToFloat();

    public StringToFloat() {
        super("to float");
    }

    @Override
    public Float of(String subject) {
        return Float.parseFloat(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Float}.
     */
    public static Feature<String,Float> stringToFloat() { return STRING_TO_FLOAT; }
}
