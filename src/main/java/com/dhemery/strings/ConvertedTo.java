package com.dhemery.strings;

import com.dhemery.core.NamedFeature;

public class ConvertedTo<T> extends NamedFeature<String, T> {
    private static final StringConverter CONVERTER = new StringConverter();
    private final Class<T> type;

    public ConvertedTo(Class<T> type) {
        super("converted to " + type.getName());
        this.type = type;
    }

    @Override
    public T of(String subject) {
        return CONVERTER.convert(subject, type);
    }
}
