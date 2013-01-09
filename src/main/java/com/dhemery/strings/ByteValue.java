package com.dhemery.strings;

import com.dhemery.core.Expression;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Byte}.
 */
public class ByteValue extends NamedFeature<String, Byte> {
    private static final ByteValue BYTE_VALUE = new ByteValue();

    public ByteValue() {
        super("Byte value");
    }

    @Override
    public Byte of(String subject) {
        return Byte.parseByte(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Byte}.
     */
    @Expression
    public static Feature<String, Byte> byteValue() {
        return BYTE_VALUE;
    }
}
