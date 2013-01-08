package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import com.dhemery.factory.Factory;

/**
 * Translates a string to a {@code Boolean}.
 */
public class BooleanValue extends NamedFeature<String, Boolean> {
    private static final BooleanValue BOOLEAN_VALUE = new BooleanValue();

    public BooleanValue() {
        super("Boolean value");
    }

    @Override
    public Boolean of(String subject) {
        return Boolean.parseBoolean(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Boolean}.
     */
    @Factory
    public static Feature<String, Boolean> booleanValue() {
        return BOOLEAN_VALUE;
    }
}
