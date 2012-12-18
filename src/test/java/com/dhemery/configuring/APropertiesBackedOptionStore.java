package com.dhemery.configuring;

import java.util.Properties;

public class APropertiesBackedOptionStore extends ABackedOptionStore {
    private final Properties properties = new Properties();

    @Override
    protected ModifiableOptionStore storeForContract() {
        return new PropertiesBackedOptionStore(properties);
    }

    protected String backingStoreValueOf(String name) {
        return properties.getProperty(name);
    }

    protected void writeToBackingStore(String name, String value) {
        properties.setProperty(name, value);
    }
}
