package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * A feature that translates its string subject to a double.
 */
public class StringToDouble extends NamedFeature<String, Double> {
    private static final Feature<String, Double> STRING_TO_DOUBLE = new StringToDouble();

    public StringToDouble() {
        super("to double");
    }

    @Override
    public Double of(String subject) {
        return Double.parseDouble(subject);
    }

    /**
     * Return a feature that translates its string subject to a double.
     */
    public static Feature<String,Double> stringToDouble() { return STRING_TO_DOUBLE; }
}
