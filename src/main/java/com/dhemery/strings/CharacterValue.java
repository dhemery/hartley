package com.dhemery.strings;

import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import com.dhemery.factory.Factory;

/**
 * Translates a string to a {@code Character}.
 */
public class CharacterValue extends NamedFeature<String, Character> {
    private static final CharacterValue CHARACTER_VALUE = new CharacterValue();

    public CharacterValue() {
        super("Character value");
    }

    @Override
    public Character of(String subject) {
        if(subject.length() != 1) throw new IllegalArgumentException("Cannot convert string \"" + subject + "\" to char.  The string must have exactly one character.");
        return subject.charAt(0);
    }

    /**
     * Return a feature that translates a string to a {@code Character}.
     */
    @Factory
    public static Feature<String, Character> characterValue() {
        return CHARACTER_VALUE;
    }
}
