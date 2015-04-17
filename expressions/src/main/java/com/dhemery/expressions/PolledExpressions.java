package com.dhemery.expressions;

import org.hamcrest.Matcher;

import java.util.function.*;

import static com.dhemery.expressions.Descriptive.condition;

/**
 * Polled composable methods to
 * evaluate boolean expressions,
 * make assertions,
 * wait for conditions,
 * and take action when preconditions are met.
 */
public interface PolledExpressions extends Poller {

    /**
     * Assert that the condition is satisfied within the schedule's duration.
     * @param schedule the schedule that governs the polling
     * @param condition the condition to satisfy
     * @throws AssertionError if the schedule's duration expires before the condition is satisfied
     */
    default void assertThat(PollingSchedule schedule, Condition condition) {
        if(!poll(schedule, condition)) throw new PolledAssertionError(schedule, condition);
    }

    /**
     * Assert that the subject satisfies the predicate within the schedule's duration.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param schedule the schedule that governs the polling
     * @throws AssertionError if the schedule's duration expires before the condition is satisfied
     */
    default <T> void assertThat(T subject, PollingSchedule schedule, Predicate<? super T> predicate) {
        Condition condition = condition(subject, predicate);
        if(!poll(schedule, condition)) throw new PolledAssertionError(schedule, condition);
    }

    /**
     * Assert that the characteristic that the function extracts from the subject satisfies the matcher within the schedule's duration.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @param schedule the schedule that governs the polling
     * @throws AssertionError if the schedule's duration expires before the condition is satisfied
     */
    default <T,V> void assertThat(T subject, Function<? super T, V> function, PollingSchedule schedule, Matcher<? super V> matcher) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(schedule, condition)) throw new PolledAssertionError(schedule, condition);
    }

    /**
     * Report whether the condition is satisfied within the schedule's duration.
     * @param schedule the schedule that governs the polling
     * @param condition the condition to satisfy
     * @return {@code true} if the condition is satisfied with the schedule's duration,
     * and {@code false} otherwise.
     */
    default boolean satisfiedThat(PollingSchedule schedule, Condition condition) {
        return poll(schedule, condition);
    }

    /**
     * Report whether the subject satisfies the predicate within the schedule's duration.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param schedule the schedule that governs the polling
     * @return {@code true} if the condition is satisfied within the schedule's duration,
     * and {@code false} otherwise.
     */
    default <T> boolean satisfiedThat(T subject, PollingSchedule schedule, Predicate<? super T> predicate) {
        return poll(schedule, condition(subject, predicate));
    }

    /**
     * Report whether the characteristic that the function extracts from the subject satisfies the matcher within the schedule's duration.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @param schedule the schedule that governs the polling
     * @return {@code true} if the condition is satisfied within the schedule's duration,
     * and {@code false} otherwise.
     */
    default <T,V> boolean satisfiedThat(T subject, Function<? super T, V> function, PollingSchedule schedule, Matcher<? super V> matcher) {
        return poll(schedule, condition(subject, function, matcher));
    }

    /**
     * Wait until the condition is satisfied.
     * @param condition the condition to satisfy
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default void waitUntil(Condition condition) {
        if(!poll(condition)) throw new PollTimeoutException(condition);
    }

    /**
     * Wait until the condition is satisfied.
     * @param schedule the schedule that governs the polling
     * @param condition the condition to satisfy
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default void waitUntil(PollingSchedule schedule, Condition condition) {
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
    }

    /**
     * Wait until the subject satisfies the predicate.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T> void waitUntil(T subject, Predicate<? super T> predicate) {
        Condition condition = condition(subject, predicate);
        if(!poll(condition)) throw new PollTimeoutException(condition);
    }

    /**
     * Wait until the subject satisfies the predicate.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param schedule the schedule that governs the polling
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T> void waitUntil(T subject, PollingSchedule schedule, Predicate<? super T> predicate) {
        waitUntil(schedule, condition(subject, predicate));
    }

    /**
     * Wait until the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T, R> void waitUntil(T subject, Function<? super T, R> function, Matcher<? super R> matcher) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(condition)) throw new PollTimeoutException(condition);
    }

    /**
     * Wait until the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @param schedule the schedule that governs the polling
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T, R> void waitUntil(T subject, Function<? super T, R> function, PollingSchedule schedule, Matcher<? super R> matcher) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
    }

    /**
     * Return the subject when it satisfies the predicate.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T> T when(T subject, Predicate<? super T> predicate) {
        Condition condition = condition(subject, predicate);
        if(!poll(condition)) throw new PollTimeoutException(condition);
        return subject;
    }

    /**
     * Return the subject when it satisfies the predicate.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param schedule the schedule that governs the polling
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T> T when(T subject, PollingSchedule schedule, Predicate<? super T> predicate) {
        Condition condition = condition(subject, predicate);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
        return subject;
    }

    /**
     * Return the subject when the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T, R> T when(T subject, Function<? super T, R> function, Matcher<? super R> matcher) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(condition)) throw new PollTimeoutException(condition);
        return subject;
    }

    /**
     * Return the subject when the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines a satisfactory value for the characteristic
     * @param schedule the schedule that governs the polling
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T, R> T when(T subject, Function<? super T, R> function, PollingSchedule schedule, Matcher<? super R> matcher) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
        return subject;
    }

    /**
     * Perform the action on the subject when the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param action the action to perform on the subject when the condition is satisfied
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T> void when(T subject, Predicate<? super T> predicate, Consumer<? super T> action) {
        PollingSchedule schedule = eventually();
        Condition condition = condition(subject, predicate);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
        action.accept(subject);
    }

    /**
     * Perform the action on the subject when the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param predicate the predicate that defines a satisfactory subject
     * @param schedule the schedule that governs the polling
     * @param action the action to perform on the subject when the condition is satisfied
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T> void when(T subject, PollingSchedule schedule, Predicate<? super T> predicate, Consumer<? super T> action) {
        Condition condition = condition(subject, predicate);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
        action.accept(subject);
    }

    /**
     * Perform the action on the subject when the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @param action the action to perform on the subject when the condition is satisfied
     * @throws PollTimeoutException if the default polling schedule's duration expires before the condition is satisfied
     */
    default <T, R> void when(T subject, Function<? super T, R> function, Matcher<? super R> matcher, Consumer<? super T> action) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(condition)) throw new PollTimeoutException(condition);
        action.accept(subject);
    }

    /**
     * Perform the action on the subject when the characteristic that the function extracts from the subject satisfies the matcher.
     * @param <T> the type of the subject
     * @param subject the subject to evaluate
     * @param function the function that extracts the characteristic to evaluate
     * @param matcher the matcher that defines satisfactory values for the characteristic
     * @param schedule the schedule that governs the polling
     * @param action the action to perform on the subject when the condition is satisfied
     * @throws PollTimeoutException if the schedule's duration expires before the condition is satisfied
     */
    default <T, R> void when(T subject, Function<? super T, R> function, PollingSchedule schedule, Matcher<? super R> matcher, Consumer<? super T> action) {
        Condition condition = condition(subject, function, matcher);
        if(!poll(schedule, condition)) throw new PollTimeoutException(schedule, condition);
        action.accept(subject);
    }
}
