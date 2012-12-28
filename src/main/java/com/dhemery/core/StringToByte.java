package com.dhemery.core;

/**
 * A feature that translates its string subject to a byte.
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
     * Return a feature that translates its string subject to a byte.
     */
    public static Feature<String,Byte> stringToByte() { return STRING_TO_BYTE; }
}
