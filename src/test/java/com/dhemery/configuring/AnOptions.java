package com.dhemery.configuring;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AnOptions {
    protected abstract ModifiableOptions optionsForContract();

    @Test
    public void getsLastDefinedValue() {
        ModifiableOptions options = optionsForContract();

        options.define("name", "old value");
        assertThat(options.option("name"), is("old value"));

        options.define("name", "new value");
        assertThat(options.option("name"), is("new value"));
    }
}
