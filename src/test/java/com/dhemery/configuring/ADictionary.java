package com.dhemery.configuring;

import com.dhemery.core.Dictionary;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class ADictionary {
    protected abstract Dictionary emptyDictionary();

    @Test
    public void getsLastDefinedValue() {
        Dictionary dictionary = emptyDictionary();

        dictionary.define("name", "old value");
        assertThat(dictionary.definitionOf("name"), is("old value"));

        dictionary.define("name", "new value");
        assertThat(dictionary.definitionOf("name"), is("new value"));
    }
}
