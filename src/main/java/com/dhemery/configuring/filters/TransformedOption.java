package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.Options;
import com.dhemery.configuring.Transformation;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

public class TransformedOption implements Option {
    private final Option option;
    private final Transformation<Option> transformation;
    private final String value;

    public TransformedOption(Option option, Transformation<Option> transformation, String value) {
        this.option = option;
        this.transformation = transformation;
        this.value = value;
    }

    @Override
    public Options source() {
        return option.source();
    }

    @Override
    public String name() {
        return option.name();
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendDescriptionOf(option)
                .appendText(" ")
                .appendDescriptionOf(transformation).appendText("->").appendValue(value);

    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
