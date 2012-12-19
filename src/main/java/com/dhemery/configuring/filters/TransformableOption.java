package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.Options;
import com.dhemery.core.Feature;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

public class TransformableOption implements Option {
    private final Option option;
    private final List<Transformation> transformations = new ArrayList<Transformation>();

    public TransformableOption(Option option) {
        this.option = option;
        transformations.add(new Transformation(option.name(), option.value()));
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
        return transformations.get(transformations.size()-1).value();
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("", " -> ", "", transformations);
    }

    public void apply(Feature<Option, String> transformation) {
        System.out.println("Applying " + transformation + " to " + this);
        transformations.add(new Transformation(transformation.toString(), transformation.of(this)));
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
