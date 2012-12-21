package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.Options;
import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class TransformableOption implements Option {
    private final Journal journal;
    private final Options source;

    public TransformableOption(Options source, String name) {
        this.source = source;
        journal = new Journal(name, source.option(name));
    }

    @Override
    public Options source() {
        return source;
    }

    @Override
    public String name() {
        return journal.name();
    }

    @Override
    public String value() {
        return journal.lastEntry();
    }

    public void apply(Feature<Option, String> transformation) {
        journal.record(transformation, transformation.of(this));
    }

    @Override
    public void describeTo(Description description) {
        journal.describeTo(description);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
