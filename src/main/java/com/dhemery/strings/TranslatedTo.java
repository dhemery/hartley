package com.dhemery.strings;

import com.dhemery.core.NamedFeature;

/**
 * Translates a string to another type.
 * @param <T> the type to translate the string to.
 */
public class TranslatedTo<T> extends NamedFeature<String, T> {
    private static final StringConverter CONVERTER = new StringConverter();
    private final Class<T> type;

    public TranslatedTo(Class<T> type) {
        super("converted to " + type.getName());
        this.type = type;
    }

    @Override
    public T of(String subject) {
        return CONVERTER.convert(subject, type);
    }
}
