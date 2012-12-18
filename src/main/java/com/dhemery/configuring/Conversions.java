package com.dhemery.configuring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Static utility methods to convert and merge properties, maps, and options.
 */
public class Conversions {
    /**
     * Create a copy of options.
     */
    public static Options copyOf(Options options) {
        return modifiableCopyOf(options);
    }

    /**
     * Create a map by copying options.
     */
    public static Map<String, String> mapOf(Options options) {
        Map<String,String> map = new HashMap<String, String>();
        mergeInto(map, options);
        return map;
    }

    public static void mergeInto(Map<String,String> destination, Map<String,String> source) {
        destination.putAll(source);
    }

    public static void mergeInto(Map<String,String> destination, Options source) {
        for(String name : source.names()) destination.put(name, source.option(name));
    }

    public static void mergeInto(Map<String, String> destination, Properties source) {
        for(String name : source.stringPropertyNames()) destination.put(name, source.getProperty(name));
    }

    /**
     * Merge options from the source into the destination.
     */
    public static void mergeInto(ModifiableOptions destination, Options source) {
        for(String name : source.names()) destination.define(name, source.option(name));
    }

    public static void mergeInto(ModifiableOptions destination, Map<String,String> source) {
        for(Map.Entry<String, String> entry : source.entrySet()) destination.define(entry.getKey(), entry.getValue());
    }

    public static void mergeInto(ModifiableOptions destination, Properties source) {
        for(String name : source.stringPropertyNames()) destination.define(name, source.getProperty(name));
    }

    public static void mergeInto(Properties destination, Options source) {
        for(String name : source.names()) destination.setProperty(name, source.option(name));
    }

    public static void mergeInto(Properties destination, Properties source) {
        for(String name : source.stringPropertyNames()) destination.setProperty(name, source.getProperty(name));
    }

    /**
     * Create a modifiable copy of the given options.
     */
    public static ModifiableOptions modifiableCopyOf(Options options) {
        ModifiableOptions copy = new MappedOptions();
        mergeInto(copy, options);
        return copy;
    }

    /**
     * Create modifiable options backed by a map.
     * Changes in the options will be written to the map.
     * Changes in the map will be reflected in the options.
     */
    public static ModifiableOptions modifiableOptionsBackedBy(Map<String,String> map) {
        return new MappedOptions(map);
    }

    /**
     * Create modifiable options backed by properties.
     * Changes in the options will be written to the properties.
     * Changes in the properties will be reflected in the options.
     */
    public static ModifiableOptions modifiableOptionsBackedBy(Properties properties) {
        return new PropertiesOptions(properties);
    }

    /**
     * Create modifiable options by copying entries from a map.
     */
    public static ModifiableOptions modifiableOptionsFrom(Map<String, String> map) {
        return modifiableCopyOf(optionsBackedBy(map));
    }

    /**
     * Create modifiable options by copying properties.
     */
    public static ModifiableOptions modifiableOptionsFrom(Properties properties) {
        return modifiableCopyOf(optionsBackedBy(properties));
    }

    /**
     * Create options backed by a map.
     * Changes in the map will be reflected in the options.
     */
    public static Options optionsBackedBy(Map<String,String> map) {
        return modifiableOptionsBackedBy(map);
    }

    /**
     * Create options backed by properties.
     * Changes in the properties will be reflected in the options.
     */
    public static Options optionsBackedBy(Properties properties) {
        return modifiableOptionsBackedBy(properties);
    }

    /**
     * Create options by copying entries from a map.
     */
    public static Options optionsFrom(Map<String, String> map) {
        return modifiableOptionsFrom(map);
    }

    /**
     * Create options by copying properties.
     */
    public static Options optionsFrom(Properties properties) {
        return modifiableOptionsFrom(properties);
    }

    /**
     * Create properties by copying options.
     */
    public static Properties propertiesFrom(Options options) {
        Properties properties = new Properties();
        mergeInto(properties, options);
        return properties;
    }
}
