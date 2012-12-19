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
     * Prepare to copy options from a source map.
     */
    public static Copy copy(Map<String,String> source) {
        return copy(optionsBackedBy(source));
    }

    /**
     * Prepare to copy options from a source set of options.
     */
    public static Copy copy(Options source) {
        return new Copy(source);
    }

    /**
     * Prepare to copy options from a source set of properties.
     */
    public static Copy copy(Properties source) {
        return copy(optionsBackedBy(source));
    }

    /**
     * Copy the options from the source into a map.
     */
    public void into(Map<String,String> destination) {
        into(optionsBackedBy(destination));
    }

    /**
     * Copy the options from the source into a set of options.
     */
    public void into(ModifiableOptions destination) {
        for(String name : source.names()) destination.define(name, source.option(name));
    }

    /**
     * Copy the options from the source into a set of properties.
     */
    public void into(Properties destination) {
        into(optionsBackedBy(destination));
    }

    /**
     * Create a configuration with options copied from the source.
     */
    public Configuration asConfiguration() {
        return new OptionsBackedConfiguration(asModifiableOptions());
    }

    /**
     * Create a map with options copied from the source.
     */
    public Map<String,String> asMap(){
        Map<String,String> map = new HashMap<String, String>();
        copy(source).into(map);
        return map;
    }

    /**
     * Create a modifiable set of options with the options copied from the source.
     */
    public ModifiableOptions asModifiableOptions(){
        ModifiableOptions destination = new MappedOptions();
        copy(source).into(destination);
        return destination;
    }

    /**
     * Create a set of options with the options copied from the source.
     */
    public Options asOptions(){
        return asModifiableOptions();
    }

    /**
     * Create a set of properties with the options copied from the source.
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
