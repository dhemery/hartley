package com.dhemery.strings;

import com.dhemery.core.Expression;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Double}.
 */
public class DoubleValue extends NamedFeature<String, Double> {
    private static final DoubleValue DOUBLE_VALUE = new DoubleValue();

    public DoubleValue() {
        super("Double value");
    }

    @Override
    public Double of(String subject) {
        return Double.parseDouble(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Double}.
     */
    @Expression
    public static Feature<String, Double> doubleValue() {
        return DOUBLE_VALUE;
    }
}
