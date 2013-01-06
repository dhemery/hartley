package com.dhemery.configuring;

import com.dhemery.core.Action;
import com.dhemery.core.Named;
import com.dhemery.core.StringDictionary;
import com.dhemery.core.UnaryOperator;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import java.util.List;
import java.util.Set;

import static com.dhemery.expressing.Evaluations.streamOf;
import static com.dhemery.expressing.OperatorExpressions.convertedTo;

/**
 * A configuration that applies a sequence of operators
 * when an option is queried.
 * <p>
 * Each call to an {@code option()} method applies the configuration operators
 * before applying any query operators.
 * <p>
 * The configuration operators do not affect the values stored in the configuration.
 * The operators are applied only by the {@code option()} methods.
 * <p>
 * Changes to the configuration are written through to the underlying dictionary.
 * The dictionary's contents,
 * and changes to its contents,
 * are reflected in the configuration.
 * @see ConfigurationBuilder
 */
@SuppressWarnings("unchecked")
public class TransformingConfiguration extends Named implements Configuration {
    private final StringDictionary dictionary;
    private final List<UnaryOperator<String>> configurationOperators;

    /**
     * Create a named configuration
     * backed by the given dictionary,
     * and with the given configuration operators.
     * @param name the name of the configuration
     * @param dictionary the {@link com.dhemery.core.StringDictionary} in which to store the configuration options.
     */
    public TransformingConfiguration(String name, StringDictionary dictionary, List<UnaryOperator<String>> configurationOperators) {
        super(name);
        this.dictionary = dictionary;
        this.configurationOperators = configurationOperators;
    }

    @Override
    public void define(String name, String value) {
        dictionary.define(name, value);
    }

    @Override
    public Set<String> names() {
        return dictionary.terms();
    }

    /**
     * Return the value of the named option,
     * transformed by the configuration and query operators.
     * @param name the name of the option
     * @param queryOperators the operators to apply after applying the configuration operators
     * @return the transformed value of the option
     * @throws com.dhemery.configuring.ConfigurationException if the transformed value is {@code null}.
     */
    @Override
    public String option(String name, UnaryOperator<String>... queryOperators) {
        return transformedOption(name, queryOperators);
    }

    /**
     * Return the value of the named option,
     * transformed by the configuration and query operators,
     * and converted to the specified type.
     * @param name the name of the option
     * @param type the type to which to convert the option
     * @param queryOperators the operators to apply after applying the configuration operators
     * @return the transformed value of the option, converted to the specified type
     * @throws com.dhemery.configuring.ConfigurationException if the transformed value is {@code null}
     * @throws IllegalArgumentException if the transformed value cannot be converted to the specified type
     */
    @Override
    public <T> T option(String name, Class<T> type, UnaryOperator<String>... queryOperators) {
        return convertedTo(type).of(option(name, queryOperators));
    }

    private String violation(String name, OptionJournal<String> journal) {
        Description description = new StringDescription();
        description.appendDescriptionOf(this)
                .appendText(" has null value for required option ")
                .appendText(name)
                .appendText("\nJournal: ")
                .appendValue(journal);
        return description.toString();
    }

    private Action<UnaryOperator<String>> recordOperationIn(final OptionJournal<String> journal) {
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

    private String transformedOption(String name, UnaryOperator<String>[] queryOperators) {
        OptionJournal<String> journal = new OptionJournal<String>(name, dictionary.definitionOf(name));
        streamOf(configurationOperators).forEach(recordOperationIn(journal));
        streamOf(queryOperators).forEach(recordOperationIn(journal));
        String value = journal.value();
        if(value != null) return value;
        throw new ConfigurationException(violation(name, journal));
    }
}
