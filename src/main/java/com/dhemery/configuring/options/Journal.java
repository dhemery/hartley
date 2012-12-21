package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import com.dhemery.core.Maybe;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

public class Journal implements SelfDescribing {
    private final List<Transaction> entries = new ArrayList<Transaction>();

    public Journal(String name, Maybe<String> initialValue) {
        record(name, initialValue);
    }

    public String name() {
        return entries.get(0).name();
    }

    public Maybe<String> lastEntry() {
        int last = entries.size() - 1;
        return entries.get(last).value();
    }

    public void record(String name, Maybe<String> value) {
        entries.add(new Transaction(name, value));
    }

    public void record(Feature<Option, Maybe<String>> transformation, Maybe<String> value) {
        record(StringDescription.asString(transformation), value);
    }

    public void describeTo(Description description) {
        description.appendList("", " -> ", "", entries);
    }
}
