package com.dhemery.configuring;

import com.dhemery.core.Named;
import com.dhemery.core.StringConverter;
import com.dhemery.core.UnaryOperator;
import com.dhemery.core.UnaryOperatorChain;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
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
        String value = option(name, operators);
        if(value != null) return value;
        throw new ConfigurationException(violation(name, operators));
    }

    @Override
    public <T> T requiredOption(String name, Class<T> type, UnaryOperator<String>... operators) {
        return converter.convert(requiredOption(name, operators), type);
    }

    private String violation(String name, UnaryOperator<String>[] operators) {
        Description description = new StringDescription();
        description.appendDescriptionOf(this)
                .appendText(" has null value for required option ")
                .appendText(name)
                .appendText("\n Details: ")
                .appendValue(diagnosisOf(name, option(name), operators));
        return description.toString();
    }

    private SelfDescribing diagnosisOf(String name, String value, UnaryOperator<String>[] operators) {
        return new RequiredOptionDiagnosis(name, value, operators);
    }

    private static  String transform(String value, UnaryOperator<String>[] operators) {
        UnaryOperator<String> transformer = new UnaryOperatorChain<String>(Arrays.asList(operators));
        return transformer.operate(value);
    }

}
