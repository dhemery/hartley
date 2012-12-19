package com.dhemery.configuring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copies options, properties, and maps.
 */
class Copy  {
    private final Options source;

    /**
     * Prepare to copy options from the source.
     */
    public Copy(Options source) {
        this.source = source;
    }

    /**
     * Prepare to copy options from a map.
     */
    public static Copy copy(Map<String,String> source) {
        return copy(optionsBackedBy(source));
    }

    /**
     * Prepare to copy options from the source options.
     */
    public static Copy copy(Options source) {
        return new Copy(source);
    }

    /**
     * Prepare to copy options from properties.
     */
    public static Copy copy(Properties source) {
        return copy(optionsBackedBy(source));
    }

    /**
     * Copy the options into a map.
     */
    public void into(Map<String,String> destination) {
        into(optionsBackedBy(destination));
    }

    /**
     * Copy the options into another set of options.
     */
    public void into(ModifiableOptions destination) {
        for(String name : source.names()) destination.define(name, source.option(name));
    }

    /**
     * Copy the options into properties.
     */
    public void into(Properties destination) {
        into(optionsBackedBy(destination));
    }

    /**
     * Copy the options as a map.
     */
    public Map<String,String> asMap(){
        Map<String,String> map = new HashMap<String, String>();
        copy(source).into(map);
        return map;
    }

    /**
     * Copy the options as modifiable options.
     */
    public ModifiableOptions asModifiableOptions(){
        ModifiableOptions destination = new MappedOptions();
        copy(source).into(destination);
        return destination;
    }

    /**
     * Copy the options as options.
     */
    public Options asOptions(){
        return asModifiableOptions();
    }

    /**
     * Copy the options as properties.
     */
    public Properties asProperties(){
        Properties properties = new Properties();
        copy(source).into(properties);
        return properties;
    }

    private static ModifiableOptions optionsBackedBy(Map<String, String> map) {
        return new MappedOptions(map);
    }

    private static ModifiableOptions optionsBackedBy(Properties properties) {
        return new PropertiesOptions(properties);
    }
}
