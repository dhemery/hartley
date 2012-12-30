package com.dhemery.configuring;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import java.util.Arrays;
import java.util.Set;

/**
 * A {@link Configuration} backed by a {@link ModifiableOptions}.
 * Changes to this configuration are written through to the underlying options.
 * Changes in the underlying options are reflected in queries of this configuration.
 */
@SuppressWarnings("unchecked")
public class OptionsBackedConfiguration extends Named implements Configuration {
    private final StringConverter converter = new StringConverter();
    private final ModifiableOptions options;

    /**
     * Create a configuration backed by an empty {@code ModifiableOptions}.
     */
    public OptionsBackedConfiguration() {
        this(new MappedOptions());
    }

    public OptionsBackedConfiguration(ModifiableOptions options) {
        this("(unnamed)", options);
    }

    /**
     * Create a configuration backed by the given options and applying the given operators.
     * @param name the name of the configuration
     * @param options the {@code ModifiableOptions} in which to store this configuration's options
     */
    public OptionsBackedConfiguration(String name, ModifiableOptions options) {
        super(name);
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
    public String option(String name, UnaryOperator<String>... operators) {
        return transform(option(name), operators);
    }

    @Override
    public String requiredOption(String name, UnaryOperator<String>... operators) {
        Description diagnosis = new StringDescription();
        UnaryOperator<String> diagnosingOperators = diagnosing(name, operators, diagnosis);
        String value = option(name, diagnosingOperators);
        if(value != null) return value;
        throw new ConfigurationException(violation(name, diagnosis));
    }

    @Override
    public <T> T requiredOption(String name, Class<T> type, UnaryOperator<String>... operators) {
        return converter.convert(requiredOption(name, operators), type);
    }

    private String violation(String name, Description diagnosis) {
        Description description = new StringDescription();
        description.appendDescriptionOf(this)
                .appendText(" has null value for required option ")
                .appendText(name)
                .appendText("\n Details: ")
                .appendValue(diagnosis);
        return description.toString();
    }

    private UnaryOperator<String> diagnosing(String name, UnaryOperator<String>[] operators, Description description) {
        return new DiagnosingUnaryOperatorChain<String>(name, Arrays.asList(operators), description);
    }

    private static  String transform(String value, UnaryOperator<String>[] operators) {
        UnaryOperator<String> transformer = new UnaryOperatorChain<String>(Arrays.asList(operators));
        return transformer.operate(value);
    }
}
