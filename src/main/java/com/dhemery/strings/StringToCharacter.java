package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;

/**
 * Translates a string to a {@code Char}.
 */
public class StringToCharacter extends NamedFeature<String, Character> {
    private static final Feature<String, Character> STRING_TO_CHARACTER = new StringToCharacter();

    public StringToCharacter() {
        super("to char");
    }

    @Override
    public Character of(String subject) {
        if(subject.length() != 1) throw new IllegalArgumentException("Cannot convert string \"" + subject + "\" to char.  The string must have exactly one character.");
        return subject.charAt(0);
    }

    /**
     * Return a feature that translates a string to a {@code Char}.
     */
    public static Feature<String,Character> stringToCharacter() { return STRING_TO_CHARACTER; }
}
