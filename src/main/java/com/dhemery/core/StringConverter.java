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

/**
 * Converts strings to the boxed representations of primitive types.
 * {@code StringConverter} knows how to convert strings to the following types:
 * <ul>
 * <li>{@link Boolean}</li>
 * <li>{@link Byte}</li>
 * <li>{@link Character}</li>
 * <li>{@link Short}</li>
 * <li>{@link Integer}</li>
 * <li>{@link Long}</li>
 * <li>{@link Float}</li>
 * <li>{@link Double}</li>
 * <li>{@link String}</li>
 * </ul>
 *
 * <p><strong>Converting to {@code String}:</strong>
 * {@code StringConverter} converts to {@code String} by returning the given string unchanged.
 *
 * <p><strong>Converting to {@code Boolean}:</strong>
 * {@code StringConverter} converts to {@code Boolean} by calling {@link Boolean#parseBoolean(String)}.
 *
 * <p><strong>Converting to {@code Character}:</strong>
 * {@code StringConverter} converts to {@code Character} by calling {@code charAt(0)}.
 * If the string's length is other than 1, the conversion throws an exception.
 *
 * <p><strong>Converting to other primitive types:</strong>
 * The other built-in target types are all numeric.
 * {@code StringConverter} converts to a numeric type by calling the type's {@code parse...()} method.
 * The string must have a value that can be converted to the type.
 * Otherwise the {@code parse...()} method throws a {@code NumberFormatException}.
 */
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

    /**
     * Convert the string to the given type.
     * @param string the string to convert
     * @param type the type to which to convert the string
     * @return the string converted to the desired type
     * @throws IllegalArgumentException if the converter does not know how to convert to the specified type
     */
    public <T> T convert(String string, Class<T> type) {
        return convert(string, to(type));
    }

    /**
     * Convert the string to the desired type by applying the given conversion.
     * @param string the string to convert
     * @param conversion a feature that can convert the string to the desired type
     * @return the string converted to the desired type
     */
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
