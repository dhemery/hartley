package com.dhemery.configuring;

import com.dhemery.core.Action;
import com.dhemery.core.StringDictionary;
import org.hamcrest.Description;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.dhemery.expressing.ImmediateExpressions.streamOf;

/**
 * Copies dictionaries, properties, and maps.
 */
class CopyTerms {
    private final StringDictionary source;

    private CopyTerms(StringDictionary dictionary) {
        source = dictionary;
    }

    /**
     * Prepare to copy terms from a dictionary.
     */
    public static CopyTerms from(StringDictionary source) {
        return new CopyTerms(source);
    }

    /**
     * Prepare to copy terms from a map.
     */
    public static CopyTerms from(Map<String,String> map) {
        return from(dictionaryOf(map));
    }

    /**
     * Prepare to copy terms from properties.
     */
    public static CopyTerms from(Properties properties) {
        return from(dictionaryOf(properties));
    }

    /**
     * Copy terms from the source into a map.
     */
    public void into(Map<String,String> destination) {
        into(dictionaryOf(destination));
    }

    /**
     * Copy terms from the source into a dictionary.
     */
    public void into(StringDictionary destination) {
        streamOf(source.terms()).forEach(copyDefinitionTo(destination));
    }

    /**
     * Copy terms from the source into a set of properties.
     */
    public void into(Properties destination) {
        into(dictionaryOf(destination));
    }

    /**
     * Create a dictionary of the terms copied from the source.
     */
    public StringDictionary asDictionary() {
        MappedDictionary destination = new MappedDictionary();
        into(destination);
        return destination;
    }

    /**
     * Create a map of the terms copied from the source.
     */
    public Map<String,String> asMap() {
        HashMap<String, String> destination = new HashMap<String, String>();
        into(destination);
        return destination;
    }

    /**
     * Create a set of properties with the terms copied from the source.
     */
    public Properties asProperties() {
        Properties destination = new Properties();
        into(destination);
        return destination;
    }

    private static StringDictionary dictionaryOf(Map<String, String> map) {
        return new MappedDictionary(map);
    }

    private static StringDictionary dictionaryOf(Properties properties) {
        return new PropertiesDictionary(properties);
    }

    private Action<String> copyDefinitionTo(final StringDictionary destination) {
        return new Action<String>() {
            @Override
            public void actOn(String name) {
                destination.define(name, source.definitionOf(name));
            }

            @Override public void describeTo(Description description) {}
        };
    }
}
