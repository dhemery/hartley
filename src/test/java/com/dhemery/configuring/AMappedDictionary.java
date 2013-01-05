package com.dhemery.configuring;

import com.dhemery.core.StringDictionary;

import java.util.HashMap;

public class AMappedDictionary extends AStringDictionary {
    private final HashMap<String, String> backingMap = new HashMap<String, String>();

    @Override
    protected StringDictionary emptyDictionary() {
        return new MappedDictionary(backingMap);
    }
}
