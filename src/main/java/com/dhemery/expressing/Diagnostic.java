package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Feature;

import java.util.Arrays;

import static com.dhemery.expressing.Evaluations.streamOf;

public class Diagnostic {
    private static final Feature<Object, String> BARE = bare();
    private static final Feature<Object, String> SINGLE_QUOTED = delimitedBy("'");
    private static final Feature<Object, String> DOUBLE_QUOTED = delimitedBy("\"");
    private static final Feature<Object, String> BYTE = number("b");
    private static final Feature<Object, String> SHORT = number("s");
    private static final Feature<Object, String> INTEGER = number("");
    private static final Feature<Object, String> LONG = number("L");
    private static final Feature<Object, String> FLOAT = number("f");
    private static final Feature<Object, String> DOUBLE = number("d");
    private static final Feature<Object, String> ITERABLE = iterable();
    private static final Feature<Object, String> ARRAY = array();

    public static String descriptionOf(Object object) {
        return converterFor(object).of(object);
    }

    public static <T> String descriptionOf(Iterable<T> items, String prefix, String separator, String suffix) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        streamOf(items).forEach(describeTo(builder, separator));
        builder.append(suffix);
        return builder.toString();
    }

    private static Feature<Object, String> converterFor(Object object) {
        if(object == null) return BARE;
        if(object instanceof Character) return SINGLE_QUOTED;
        if(object instanceof String) return DOUBLE_QUOTED;
        if(object instanceof Byte) return BYTE;
        if(object instanceof Short) return SHORT;
        if(object instanceof Integer) return INTEGER;
        if(object instanceof Long) return LONG;
        if(object instanceof Float) return FLOAT;
        if(object instanceof Double) return DOUBLE;
        if(object instanceof Iterable) return ITERABLE;
        if(object.getClass().isArray()) return ARRAY;
        return BARE;
    }

    private static <T> Action<T> describeTo(final StringBuilder builder, final String separator) {
        return new Action<T>() {
            boolean separate;
            @Override
            public void actOn(T subject) {
                if(separate) builder.append(separator);
                builder.append(descriptionOf(subject));
                separate = true;
            }
        };
    }

    private static Feature<Object, String> bare() {
        return new Feature<Object, String>() {
            @Override
            public String of(Object subject) {
                return String.valueOf(subject);
            }
        };
    }

    private static Feature<Object, String> delimitedBy(final String delimiter) {
        return new Feature<Object, String>() {
            @Override
            public String of(Object subject) {
                return delimiter + subject + delimiter;
            }
        };
    }

    private static Feature<Object, String> number(final String marker) {
        return new Feature<Object, String>() {
            @Override
            public String of(Object number) {
                return "<" + number + marker + '>';
            }
        };
    }

    private static Feature<Object, String> iterable() {
        return new Feature<Object, String>() {
            @Override
            public String of(Object iterable) {
                return descriptionOf((Iterable) iterable, "[", ",", "]");
            }
        };
    }

    private static Feature<Object, String> array() {
        return new Feature<Object, String>() {
            @Override
            public String of(Object array) {
                return descriptionOf(Arrays.asList((Object[]) array));
            }
        };
    }
}
