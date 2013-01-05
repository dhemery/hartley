package com.dhemery.configuring;

import com.dhemery.core.StringDictionary;
import com.dhemery.core.PropertiesDictionary;

import java.util.Properties;

public class APropertiesDictionary extends AStringDictionary {
    private final Properties properties = new Properties();

    @Override
    protected StringDictionary emptyDictionary() {
        return new PropertiesDictionary(properties);
    }
}
