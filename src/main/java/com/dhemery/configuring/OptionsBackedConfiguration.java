package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

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
    private final List<UnaryOperator<String>> configurationOperators;

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
    public OptionsBackedConfiguration(ModifiableOptions options, final List<UnaryOperator<String>> configurationOperators) {
        this.options = options;
        this.configurationOperators = configurationOperators;
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
        return translate(options.option(name), configurationOperators, new Journal());
    }

    @Override
    public String option(String name, UnaryOperator<String>... queryOperators) {
        return translate(option(name), Arrays.asList(queryOperators), new Journal());
    }

    @Override
    public String requiredOption(String name, UnaryOperator<String>... queryOperators) {
        Journal journal = new Journal();
        String value = options.option(name);
        journal.record(name, value);
        value = translate(value, configurationOperators, journal);
        value = translate(value, Arrays.asList(queryOperators), journal);
        if(value != null) return value;
        Description description = new StringDescription();
        description.appendText("Missing value for required configuration option ")
                .appendText(name)
                .appendText("\nTried: ")
                .appendDescriptionOf(journal);
        throw new ConfigurationException(description.toString());
    }

    private String translate(String value, List<UnaryOperator<String>> operators, Journal journal) {
        for(UnaryOperator<String> operator : operators) {
            value = operator.operate(value);
            journal.record(operator.toString(), value);
        }
        return value;
    }
}
