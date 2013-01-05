package com.dhemery.configuring;

import com.dhemery.core.StringDictionary;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AStringDictionary {
    protected abstract StringDictionary emptyDictionary();

    @Test
    public void getsLastDefinedValue() {
        StringDictionary dictionary = emptyDictionary();

        dictionary.define("name", "old value");
        assertThat(dictionary.definitionOf("name"), is("old value"));

        dictionary.define("name", "new value");
        assertThat(dictionary.definitionOf("name"), is("new value"));
    }
}
