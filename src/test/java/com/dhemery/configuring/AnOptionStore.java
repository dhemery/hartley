package com.dhemery.configuring;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AnOptionStore {
    protected abstract ModifiableOptionStore storeForContract();

    @Test
    public void getsLastDefinedValue() {
        ModifiableOptionStore store = storeForContract();

        store.define("name", "old value");
        assertThat(store.option("name"), is("old value"));

        store.define("name", "new value");
        assertThat(store.option("name"), is("new value"));
    }
}
