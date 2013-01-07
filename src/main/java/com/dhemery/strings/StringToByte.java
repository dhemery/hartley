package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Byte}.
 */
public class StringToByte extends NamedFeature<String, Byte> {
    private static final Feature<String, Byte> STRING_TO_BYTE = new StringToByte();

    public StringToByte() {
        super("to byte");
    }

    @Override
    public Byte of(String subject) {
        return Byte.parseByte(subject);
    }

    /**
     * Return a feature that translates a string to a {@code Byte}.
     */
    public static Feature<String,Byte> stringToByte() { return STRING_TO_BYTE; }
}
