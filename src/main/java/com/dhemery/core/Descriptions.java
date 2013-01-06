package com.dhemery.core;

import com.dhemery.strings.Stringifier;

import static com.dhemery.expressing.Evaluations.streamOf;

public class Descriptions {
    private static Stringifier STRINGIFIER = new Stringifier();

    public static <T> String descriptionOf(Iterable<T> items, String prefix, String separator, String suffix) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        streamOf(items).forEach(describeTo(builder, separator));
        builder.append(suffix);
        return builder.toString();
    }

    private static <T> Action<T> describeTo(final StringBuilder builder, final String separator) {
        return new Action<T>() {
            boolean separate;
            @Override
            public void actOn(T subject) {
                if(separate) builder.append(separator);
                builder.append(subject);
                separate = true;
            }

        };
    }

    public static <T> String descriptionOf(T object) {
        return STRINGIFIER.stringify(object);
    }
}
