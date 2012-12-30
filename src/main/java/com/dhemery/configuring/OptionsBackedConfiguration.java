package com.dhemery.configuring;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.dhemery.expressing.ImmediateExpressions.streamOf;

/**
 * A {@link Configuration} backed by a {@link ModifiableOptions}.
 * Changes to this configuration are written through to the underlying options.
 * Changes in the underlying options are reflected in queries of this configuration.
 */
@SuppressWarnings("unchecked")
public class OptionsBackedConfiguration extends Named implements Configuration {
    private final StringConverter converter = new StringConverter();
    private final ModifiableOptions options;
    private final List<UnaryOperator<String>> configurationOperators;

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this("(unnamed configuration)", options);
    }

    public OptionsBackedConfiguration(String name, ModifiableOptions options) {
        this(name, options, Collections.<UnaryOperator<String>>emptyList());
    }

    /**
     * Create a configuration backed by the given options and applying the given operators.
     * @param name the name of the configuration
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(String name, ModifiableOptions options, List<UnaryOperator<String>> operators) {
        super(name);
        this.options = options;
        configurationOperators = operators;
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
        return journalOf(name).value();
    }

    @Override
    public String option(String name, UnaryOperator<String>... operators) {
        return journalOf(name, operators).value();
    }

    @Override
    public String requiredOption(String name, UnaryOperator<String>... operators) {
        Journal<String> journal = journalOf(name, operators);
        String value = journal.value();
        if(value != null) return value;
        throw new ConfigurationException(violation(name, journal));
    }

    @Override
    public <T> T requiredOption(String name, Class<T> type, UnaryOperator<String>... operators) {
        return converter.convert(requiredOption(name, operators), type);
    }

    private String violation(String name, Journal<String> journal) {
        Description description = new StringDescription();
        description
                .appendText(System.lineSeparator())
                .appendDescriptionOf(this)
                .appendText(" has null value for required option ")
                .appendText(name)
                .appendText(System.lineSeparator())
                .appendText("Journal: ")
                .appendValue(journal);
        return description.toString();
    }

    private Journal<String> journalOf(String name, UnaryOperator<String>... queryOperators) {
        Journal<String> journal = new Journal<String>(name, options.option(name));
        streamOf(configurationOperators).forEach(recordOperationIn(journal));
        streamOf(queryOperators).forEach(recordOperationIn(journal));
        return journal;
    }

    private Action<UnaryOperator<String>> recordOperationIn(final Journal<String> journal) {
        return new Action<UnaryOperator<String>>() {
            @Override
            public void actOn(UnaryOperator<String> operator) {
                String operand = journal.value();
                String result = operator.operate(operand);
                journal.record(operator.toString(), result);
            }

            @Override public void describeTo(Description description) {}
        };
    }
}
