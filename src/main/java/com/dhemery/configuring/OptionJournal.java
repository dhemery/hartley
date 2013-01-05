package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

class OptionJournal<T> implements SelfDescribing {
    private final List<OptionTransaction<T>> entries = new ArrayList<OptionTransaction<T>>();

    public OptionJournal(String name, T initialValue) {
        record(name, initialValue);
    }

    public void record(String name, T value) {
        entries.add(new OptionTransaction<T>(name, value));
    }

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

}
