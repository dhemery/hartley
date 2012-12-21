package com.dhemery.configuring;

import com.dhemery.configuring.options.TransformableOption;
import com.dhemery.core.Feature;
import com.dhemery.expressing.ImmediateExpressions;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.dhemery.configuring.options.OptionExpressions.value;
import static org.hamcrest.Matchers.*;

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
        return filter(name, transformations).value();
    }

    @Override
    public String option(String name, Feature<Option,String>... transformations) {
        return filter(name, Arrays.asList(transformations)).value();
    }

    @Override
    public String requiredOption(String name, Feature<Option,String>... transformations) {
        Option option = filter(name, Arrays.asList(transformations));
        assertThat(option, value(), is(not(nullValue())));
        return option.value();
    }

    private static void assertThat(Option option, Feature<Option, String> feature, Matcher<Object> criteria) {
        try {
            ImmediateExpressions.assertThat(option, feature, criteria);
        } catch (AssertionError cause) {
            throw new ConfigurationException("Missing value for required configuration option " + option.name(), cause);
        }
    }

    private Option filter(String name, List<Feature<Option,String>> transformations) {
        TransformableOption option = new TransformableOption(options, name);
        for(Feature<Option,String> transformation : transformations) {
            option.apply(transformation);
        }
        return option;
    }
}
