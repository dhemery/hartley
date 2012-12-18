package com.dhemery.configuring;

import java.util.Properties;

public class APropertiesBackedOptions extends ABackedOptions {
    private final Properties properties = new Properties();

    @Override
    protected ModifiableOptions optionsForContract() {
        return new PropertiesOptions(properties);
    }

    @Override
    protected String backingStoreValueOf(String name) {
        return properties.getProperty(name);
    }

    @Override
    protected void writeToBackingStore(String name, String value) {
        properties.setProperty(name, value);
    }
}
