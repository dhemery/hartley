package com.dhemery.configuring;

import com.dhemery.core.Descriptions;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

class OptionJournal<T> {
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
    public String toString() {
        new StringDescription();
        return new StringBuilder()
                .append(Descriptions.descriptionOf(entries, "<", " -> ", ">"))
                .toString();
    }
}
