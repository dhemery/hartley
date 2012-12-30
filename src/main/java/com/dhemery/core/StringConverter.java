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

public class StringConverter {
    private final Map<Class<?>,Feature<String,?>> translatorsByType = new HashMap<Class<?>, Feature<String,?>>();

    public StringConverter() {
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

    public <T> T convert(String string, Class<T> type) {
        return convert(string, to(type));
    }

    public <T> T convert(String string, Feature<String,T> conversion) {
        return conversion.of(string);
    }

    private <T> Feature<String,T> to(Class<T> type) {
        if(!translatorsByType.containsKey(type)) throw new IllegalArgumentException(explainInabilityToConvertTo(type));
        return (Feature<String,T>) translatorsByType.get(type);
    }

    private <T> String explainInabilityToConvertTo(Class<T> type) {
        return new StringBuilder()
                .append(getClass().getSimpleName())
                .append(" does not know how to convert a string to ")
                .append(type)
                .append('\n')
                .append("Known conversion target types are: ")
                .append(translatorsByType.keySet())
                .toString();
    }

    private <T> void add(Class<T> type, Feature<String, T> translator) {
        translatorsByType.put(type, translator);
    }
}
