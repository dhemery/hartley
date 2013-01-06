package com.dhemery.strings;

import com.dhemery.core.Feature;

import java.util.HashMap;
import java.util.Map;

public class Stringifier {
    private final Map<Class<?>,Feature<Object,String>> convertersByType = new HashMap<Class<?>, Feature<Object, String>>();

    public Stringifier() {
        add(Character.class, converter("'", "'"));
        add(Byte.class, converter("<", "b>"));
        add(Short.class, converter("<", "s>"));
        add(Integer.class, converter("<", ">"));
        add(Long.class, converter("<", "L>"));
        add(Float.class, converter("<", "f>"));
        add(Double.class, converter("<", "d>"));
        add(String.class, converter("\"", "\""));
        add(Object.class, converter("", ""));
    }

    public String stringify(Object object) {
        if(object == null) return "null";
        Feature<Object, String> stringifier = stringifier(object.getClass());
        return stringifier.of(object);
    }

    private Feature<Object,String> converter(final String prefix, final String suffix) {
        return new Feature<Object, String>() {
            @Override
            public String of(Object subject) {
                return prefix + subject + suffix;
            }
        };
    }

    private <T> void add(Class<T> type, Feature<Object, String> converter) {
        convertersByType.put(type, converter);
    }

    private Feature<Object,String> stringifier(Class<?> type) {
        if(convertersByType.containsKey(type)) return (Feature) convertersByType.get(type);
        return (Feature) convertersByType.get(Object.class);
    }
}
