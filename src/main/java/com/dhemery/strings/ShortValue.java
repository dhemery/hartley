package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import com.dhemery.factory.Factory;

/**
 * Translates a string to a {@code Short}.
 */
public class ShortValue extends NamedFeature<String, Short> {
    private static final ShortValue SHORT_VALUE = new ShortValue();

    public ShortValue() {
        super("Short value");
    }

    @Override
    public Short of(String subject) {
        return Short.parseShort(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Short}.
     */
    @Factory
    public static Feature<String, Short> shortValue() {
        return SHORT_VALUE;
    }
}
