package com.dhemery.configuring;

import com.dhemery.core.Feature;

import java.util.Set;

import static com.dhemery.configuring.OptionExpressions.defined;
import static com.dhemery.configuring.OptionExpressions.require;

/**
 * A {@link Configuration} backed by a {@link ModifiableOptions}.
 * Changes to this configuration are written through to the underlying options.
 * Changes in the underlying options are reflected in queries of this configuration.
 */
@SuppressWarnings("unchecked")
public class OptionsBackedConfiguration implements Configuration {
    private final ModifiableOptions options;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options) {
        this.options = options;
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
        return options.option(name);
    }

    @Override
    public String option(String name, Feature<Option,String>... filters) {
        return new FilteringOptions(options, filters).option(name);
    }

    @Override
    public String requiredOption(String name) {
        return option(name, require(defined()));
    }
}
