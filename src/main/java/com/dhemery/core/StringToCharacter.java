package com.dhemery.core;

/**
 * A feature that translates its string subject to a char.
 */
public class StringToCharacter extends NamedFeature<String, Character> {
    private static final Feature<String, Character> STRING_TO_CHARACTER = new StringToCharacter();

    public StringToCharacter() {
        super("to char");
    }

    @Override
    public Character of(String subject) {
        if(subject.length() != 1) throw new IllegalArgumentException("Cannot translate \"" + subject + "\" to char");
        return subject.charAt(0);
    }

    /**
     * Return a feature that translates its string subject to a char.
     */
    public static Feature<String,Character> stringToCharacter() { return STRING_TO_CHARACTER; }
}