package com.dhemery.expressions;

import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.joining;

/**
 * An object with a fixed description.
 * The {@code toString()} method
 * returns the fixed description.
 */
public class Descriptive {
    private final String description;

    /**
     * Create an object with the given description.
     */
    public Descriptive(String description) {
        this.description = description;
    }

    /**
     * Return this object's fixed description
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * Create a {@link Condition}
     * with the given description
     * and underlying condition.
     */
    public static Condition condition(String name, Condition condition) {
        return new DescriptiveCondition(name, condition);
    }

    /**
     * Create a descriptive {@link Condition} that is satisfied
     * if its {@code subject} satisfies its {@code predicate}.
     * This condition's description
     * describes the subject and predicate.
     * @param subject the subject to evaluate
     * @param predicate defines a satisfactory subject
     * @param <T> the type of the subject
     * @return a condition that is satisfied
     * if the subject satisfies the predicate
     * @see SubjectSatisfiesPredicate
     */
    public static <T> Condition condition(T subject, Predicate<? super T> predicate) {
        return new SubjectSatisfiesPredicate<>(subject, predicate);
    }

    /**
     * Create a descriptive {@link Condition} that is satisfied
     * if the characteristic extracted from its {@code subject} by its {@code function}
     * satisfies its {@code matcher}.
     * This condition's description
     * describes the subject, function, and matcher.
     * @param subject the subject to evaluate
     * @param function extracts the pertinent characteristic from the subject
     * @param matcher defines satisfactory values for the characteristic
     * @param <T> the type of the subject
     * @param <R> the type of the function result
     * @return a condition that is satisfied
     * if the result of applying the function to the subject
     * satisfies the matcher
     * @see FunctionOfSubjectSatisfiesMatcher
     */
    public static <T, R> Condition condition(T subject, Function<? super T, ? extends R> function, Matcher<? super R> matcher) {
        return new FunctionOfSubjectSatisfiesMatcher<>(subject, function, matcher);
    }

    /**
     * Create a {@link Consumer}
     * with the given description
     * and underlying consumer.
     * @see DescriptiveConsumer
     */
    public static <T> Consumer<T> consumer(String description, Consumer<? super T> consumer) {
        return new DescriptiveConsumer<>(description, consumer);
    }

    /**
     * Create a {@link Function}
     * with the given description
     * and underlying function.
     * @see DescriptiveFunction
     */
    public <T,R> Function<T,R> function(String description, Function<? super T, ? extends R> function) {
        return new DescriptiveFunction<>(description, function);
    }

    /**
     * Create a {@link Predicate}
     * with the given description
     * and underlying predicate.
     * @see DescriptivePredicate
     */
    public <T> Predicate<T> predicate(String description, Predicate<? super T> predicate) {
        return new DescriptivePredicate<>(description, predicate);
    }

    /**
     * Join the string values of the description parts, separated by spaces.
     */
    public static String describedAs(Object... descriptionParts) {
        return Arrays.stream(descriptionParts).map(Object::toString).collect(joining(" "));
    }
}