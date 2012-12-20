package com.dhemery.configuring;

import com.dhemery.core.Feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A {@link Configuration} backed by a {@link ModifiableOptions}.
 * Changes to this configuration are written through to the underlying options.
 * Changes in the underlying options are reflected in queries of this configuration.
 */
@SuppressWarnings("unchecked")
public class OptionsBackedConfiguration implements Configuration {
    private final ModifiableOptions options;
    private final List<Feature<Option, String>> transformations;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this(options, new ArrayList<Feature<Option,String>>());
    }
    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options, List<Feature<Option,String>> transformations) {
        this.options = options;
        this.transformations = transformations;
    }

    @Override
    public void define(String name, String value) {
        options.define(name, value);
    }

    @Override
    public boolean defines(String name) {
        return options.names().contains(name);
    }

    @Override
    public Set<String> names() {
        return options.names();
    }

    @Override
    public String option(String name) {
        return filter(name, transformations);
    }

    @Override
    public String option(String name, Feature<Option,String>... transformations) {
        return filter(name, Arrays.asList(transformations));
    }

    private String filter(String name, List<Feature<Option,String>> transformations) {
        TransformableOption option = new TransformableOption(options, name);
        for(Feature<Option,String> transformation : transformations) {
            option.apply(transformation);
        }
        return option.value();
    }

    @Override
    public String requiredOption(String name) {
        return null;
    }
}
