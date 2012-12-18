package com.dhemery.configuring;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public abstract class ABackedOptionStore extends AnOptionStore {
    protected abstract String backingStoreValueOf(String name);
    protected abstract void writeToBackingStore(String name, String value);

    @Test
    public void getsOptionsFromBackingStore() {
        ModifiableOptionStore store = storeForContract();

        writeToBackingStore("gumby", "pokey");
        writeToBackingStore("lippy the lion", "hardy har har");

        assertThat(store.option("gumby"), is("pokey"));
        assertThat(store.option("lippy the lion"), is("hardy har har"));
    }

    @Test
    public void getsAllNamesFromBackingMap() {
        writeToBackingStore("name1", "value1");
        writeToBackingStore("name2", "value2");
        writeToBackingStore("name3", "value3");
        writeToBackingStore("name4", "value4");
        writeToBackingStore("name5", "value5");
        writeToBackingStore("name6", "value6");

        assertThat(storeForContract().names(), containsInAnyOrder("name1", "name2", "name3", "name4", "name5", "name6"));
    }

    @Test
    public void writesOptionsToBackingStore() {
        storeForContract().define("foo", "bar");
        assertThat(backingStoreValueOf("foo"), is("bar"));
    }
}
