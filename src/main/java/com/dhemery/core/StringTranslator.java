package com.dhemery.core;

import java.util.HashMap;
import java.util.Map;

import static com.dhemery.core.StringToBoolean.stringToBoolean;
import static com.dhemery.core.StringToByte.stringToByte;
import static com.dhemery.core.StringToCharacter.stringToCharacter;
import static com.dhemery.core.StringToDouble.stringToDouble;
import static com.dhemery.core.StringToFloat.stringToFloat;
import static com.dhemery.core.StringToInteger.stringToInteger;
import static com.dhemery.core.StringToLong.stringToLong;
import static com.dhemery.core.StringToShort.stringToShort;

public class StringTranslator {
    private final Map<Class<?>,Feature<String,?>> translatorsByType = new HashMap<Class<?>, Feature<String,?>>();

    public StringTranslator() {
        add(Boolean.class, stringToBoolean());
        add(Byte.class, stringToByte());
        add(Character.class, stringToCharacter());
        add(Short.class, stringToShort());
        add(Integer.class, stringToInteger());
        add(Long.class, stringToLong());
        add(Float.class, stringToFloat());
        add(Double.class, stringToDouble());
        add(String.class, Self.<String>self());
    }

    public <T> T translate(String string, Class<T> type) {
        return translationTo(type).of(string);
    }

    private <T> Feature<String,T> translationTo(Class<T> type) {
        if(!translatorsByType.containsKey(type)) throw new IllegalArgumentException("Cannot translate a string to " + type);
        return (Feature<String,T>) translatorsByType.get(type);
    }

    private <T> void add(Class<T> type, Feature<String, T> translator) {
        translatorsByType.put(type, translator);
    }
}
