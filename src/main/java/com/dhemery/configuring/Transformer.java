package com.dhemery.configuring;

import com.dhemery.configuring.filters.TransformableOption;
import com.dhemery.core.Feature;

import java.util.List;

public class Transformer{
    private final List<Feature<Option,String>> transformations;

    public Transformer(List<Feature<Option,String>> transformations) {
        this.transformations = transformations;
    }

    public Option transform(Option option) {
        TransformableOption transformed = new TransformableOption(option);
        for(Feature<Option,String> transformation : transformations) {
            transformed.apply(transformation);
        }
        return transformed;
    }
}
