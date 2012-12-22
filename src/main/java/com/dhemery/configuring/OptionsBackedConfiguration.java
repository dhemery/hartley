package com.dhemery.configuring;

import com.dhemery.core.CompoundUnaryOperator;
import com.dhemery.core.Maybe;
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
    private final UnaryOperator<Maybe<String>> configurationOperator;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this(options, Collections.<UnaryOperator<Maybe<String>>>emptyList());
    }

    /**
     * Create a configuration backed by the given options.
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(ModifiableOptions options, final List<UnaryOperator<Maybe<String>>> operators) {
        this.options = options;
        configurationOperator = compound(operators);
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
        for(String value : maybe(name)) return value;
        return null;
    }

    @Override
    public String option(String name, UnaryOperator<Maybe<String>>... operators) {
        for(String value : maybe(name, operators)) return value;
        return null;
    }

    @Override
    public String requiredOption(String name, UnaryOperator<Maybe<String>>... operators) {
        for(String value : maybe(name, operators)) return value;
        throw new ConfigurationException("Problem with configuration option " + name);
    }

    @Override
    public Maybe<String> maybe(String name) {
        return configurationOperator.operate(options.maybe(name));
    }

    private Maybe<String> maybe(String name, UnaryOperator<Maybe<String>>[] operators) {
        return compound(operators).operate(maybe(name));
    }

    private <T> UnaryOperator<T> compound(UnaryOperator<T>[] operators) {
        return compound(Arrays.asList(operators));
    }

    private static <T> UnaryOperator<T> compound(List<UnaryOperator<T>> operators) {
        return new CompoundUnaryOperator(operators);
    }
}
