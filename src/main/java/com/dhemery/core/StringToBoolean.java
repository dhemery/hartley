package com.dhemery.core;

/**
 * A feature that translates its string subject to a boolean.
 */
public class StringToBoolean extends NamedFeature<String,Boolean> {
    private static final Feature<String, Boolean> STRING_TO_BOOLEAN = new StringToBoolean();

    public StringToBoolean() {
        super("to boolean");
    }

    @Override
    public Boolean of(String subject) {
        return Boolean.parseBoolean(subject);
    }

    /**
     * Return a feature that translates its string subject to a boolean.
     */
    public static Feature<String,Boolean> stringToBoolean() { return STRING_TO_BOOLEAN; }
}
