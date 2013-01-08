package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import com.dhemery.factory.Factory;

/**
 * Translates a string to a {@code Float}.
 */
public class FloatValue extends NamedFeature<String, Float> {
    private static final FloatValue FLOAT_VALUE = new FloatValue();

    public FloatValue() {
        super("Float value");
    }

    @Override
    public Float of(String subject) {
        return Float.parseFloat(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Float}.
     */
    @Factory
    public static Feature<String, Float> floatValue() {
        return FLOAT_VALUE;
    }
}
