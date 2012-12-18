package com.dhemery.configuring;

import java.util.HashMap;

public class AMapBackedOptionStore extends ABackedOptionStore {
    private final HashMap<String, String> backingMap = new HashMap<String, String>();

    @Override
    protected ModifiableOptionStore storeForContract() {
        return new MapBackedOptionStore(backingMap);
    }

    protected String backingStoreValueOf(String name) {
        return backingMap.get(name);
    }

    protected void writeToBackingStore(String name, String value) {
        backingMap.put(name, value);
    }
}
