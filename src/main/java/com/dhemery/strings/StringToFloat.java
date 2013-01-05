package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * A feature that translates its string subject to a float.
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
     * Return a feature that translates its string subject to a float.
     */
    public static Feature<String,Float> stringToFloat() { return STRING_TO_FLOAT; }
}
