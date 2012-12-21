package com.dhemery.configuring;

import com.dhemery.configuring.options.TransformableOption;
import com.dhemery.core.Feature;
import com.dhemery.core.Maybe;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

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
    private final List<Feature<Option, Maybe<String>>> transformations;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this(options, new ArrayList<Feature<Option,Maybe<String>>>());
    }
    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options, List<Feature<Option,Maybe<String>>> transformations) {
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
    public String option(String name, Feature<Option, Maybe<String>>... transformations) {
        List<Feature<Option, Maybe<String>>> list = Arrays.asList(transformations);
        Option filtered = filter(name, list);
        for(String value : filtered.value()) return value;
        return null;
    }

    @Override
    public Set<String> names() {
        return options.names();
    }

    @Override
    public String option(String name) {
        Option filtered = filter(name, transformations);
        for(String value : filtered.value()) return value;
        return null;
    }

    @Override
    public Maybe<String> maybe(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String requiredOption(String name, Feature<Option,Maybe<String>>... transformations) {
        Option option = filter(name, Arrays.asList(transformations));
        for(String value : option.value()) { return value; }
        Description description = new StringDescription();
        description.appendText("Missing value for required configuration option ")
                .appendText(option.name())
                .appendText("\nFound: ")
                .appendValue(option);
        throw new ConfigurationException(description.toString());
    }

    private Option filter(String name, List<Feature<Option,Maybe<String>>> transformations) {
        TransformableOption option = new TransformableOption(options, name);
        for(Feature<Option,Maybe<String>> transformation : transformations) {
            option.apply(transformation);
        }
        return option;
    }
}
