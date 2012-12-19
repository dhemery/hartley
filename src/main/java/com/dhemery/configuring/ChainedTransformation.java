package com.dhemery.configuring;

import org.hamcrest.Description;

import java.util.List;

public class ChainedTransformation implements Transformation<Option> {
    private final List<Transformation<Option>> transformations;

    public ChainedTransformation(List<Transformation<Option>> transformations) {
        this.transformations = transformations;
    }

    @Override
    public Option of(Option option) {
        for(Transformation<Option> transformation : transformations) option = transformation.of(option);
        return option;
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("(", " and ", ")", transformations);
    }
}
