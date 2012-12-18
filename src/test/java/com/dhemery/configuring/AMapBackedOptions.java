package com.dhemery.configuring;

import java.util.HashMap;

public class AMapBackedOptions extends ABackedOptions {
    private final HashMap<String, String> backingMap = new HashMap<String, String>();

    @Override
    protected ModifiableOptions optionsForContract() {
        return new MappedOptions(backingMap);
    }

    @Override
    protected String backingStoreValueOf(String name) {
        return backingMap.get(name);
    }

    @Override
    protected void writeToBackingStore(String name, String value) {
        backingMap.put(name, value);
    }
}
