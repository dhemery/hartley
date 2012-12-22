package com.dhemery.configuring;

import com.dhemery.core.UnaryOperatorSequence;
import com.dhemery.core.UnaryOperator;

import java.util.Arrays;
import java.util.Collections;
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
    private final UnaryOperator<String> configurationOperator;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this(options, Collections.<UnaryOperator<String>>emptyList());
    }

    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options, final List<UnaryOperator<String>> operators) {
        this.options = options;
        configurationOperator = sequence(operators);
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
        return configurationOperator.operate(options.option(name));
    }

    @Override
    public String option(String name, UnaryOperator<String>... queryOperators) {
        return sequence(queryOperators).operate(option(name));
    }

    @Override
    public String requiredOption(String name, UnaryOperator<String>... queryOperators) {
        String result = option(name, queryOperators);
        if(result != null) return result;
        throw new ConfigurationException("Missing value for required configuration option " + name);
    }


    public static <T> UnaryOperator<T> sequence(UnaryOperator<T>[] operators) {
        return sequence(Arrays.asList(operators));
    }

    public static <T> UnaryOperator<T> sequence(List<UnaryOperator<T>> operators) {
        return new UnaryOperatorSequence(operators);
    }
}
