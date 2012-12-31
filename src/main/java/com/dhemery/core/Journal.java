package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * A journal of named values.
 * The "value" of the journal is the most recently recorded value.
 * @param <T> the type of value recorded in the journal
 */
public class Journal<T> implements SelfDescribing {
    private final List<Transaction<T>> entries = new ArrayList<Transaction<T>>();

    /**
     * Create a journal of named values.
     * The initial value is recorded as the first entry.
     * @param name the name of the initial value
     * @param initialValue the value to record as the first entry
     */
    public Journal(String name, T initialValue) {
        record(name, initialValue);
    }

    /**
     * Record the value in the journal with the given name.
     * @param name the name of this value
     * @param value the value to record
     */
    public void record(String name, T value) {
        entries.add(new Transaction<T>(name, value));
    }

    /**
     * Return the most recently recorded value.
     */
    public T value() {
        int last = entries.size() - 1;
        return entries.get(last).value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("", " -> ", "", entries);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }

    private class Transaction<T> implements SelfDescribing {
        public final String name;
        public final T value;

        public Transaction(String name, T value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendText("[")
                    .appendText(name)
                    .appendText(":")
                    .appendValue(value)
                    .appendText("]");
        }
    }
}
