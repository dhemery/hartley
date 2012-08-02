package com.dhemery.polling;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public abstract class Expressive {
    private static final Matcher<Boolean> IS_QUIETLY_TRUE = new TypeSafeMatcher<Boolean>() {
        @Override
        protected boolean matchesSafely(Boolean value) {
            return value;
        }

        @Override
        public void describeTo(Description description) {}
    };

    private final Lazy<Poller> defaultPoller = Lazily.get(new Supplier<Poller>() {
        @Override
        public Poller get() {
            return defaultPoller();
        }
    });

    /**
     * Implement this method
     * to deliver the default {@link Poller}
     * for use in expressions.
     * {@code Expressive} will call {@code defaultPoller()}
     * at most one time.
     * @return the default poller.
     */
    protected abstract Poller defaultPoller();

    /**
     * <p>Return the default poller.
     * This method is named to read nicely in expressions:
     * </p>
     * <pre>
     * {@code
     * assertThat(theCheshireCat, eventually(), isGrinning());
     * }
     * </pre>
     */
    protected Poller eventually() {
        return defaultPoller.get();
    }

    public static void assertThat(Condition condition) {
        ConditionAssert.assertThat(condition);
    }

    public static void assertThat(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    public static <V> void assertThat(Sampler<V> sampler, Matcher<? super V> criteria) {
        assertThat(condition(sampler, criteria));
    }

    public static void assertThat(Sampler<Boolean> sampler) {
        assertThat(sampler, IS_QUIETLY_TRUE);
    }

    public static <V> void assertThat(Sampler<V> sampler, Poller poller, Matcher<? super V> criteria) {
        assertThat(poller, condition(sampler, criteria));
    }

    public static void assertThat(Poller poller, Sampler<Boolean> sampler) {
        assertThat(sampler, poller, IS_QUIETLY_TRUE);
    }

    public static <S,V> void assertThat(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        assertThat(sampler(subject, query), criteria);
    }

    public static <S> void assertThat(S subject, Query<? super S,Boolean> query) {
        assertThat(subject, query, IS_QUIETLY_TRUE);
    }

    public static <S,V> void assertThat(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
        assertThat(sampler(subject, query), poller, criteria);
    }

    public static <S> void assertThat(S subject, Poller poller, Query<? super S,Boolean> query) {
        assertThat(subject, query, poller, IS_QUIETLY_TRUE);
    }

    public static boolean the(Condition condition) {
        return condition.isSatisfied();
    }

    public static boolean the(Condition condition, Poller poller) {
        try {
            poller.poll(condition);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    public static <V> boolean the(Sampler<V> sampler, Matcher<? super V> criteria) {
        return the(condition(sampler, criteria));
    }

    public static boolean the(Sampler<Boolean> sampler) {
        return the(sampler, IS_QUIETLY_TRUE);
    }

    public static <V> boolean the(Sampler<V> sampler, Poller poller, Matcher<? super V> criteria) {
        return the(condition(sampler, criteria), poller);
    }

    public static boolean the(Sampler<Boolean> sampler, Poller poller) {
        return the(sampler, poller, IS_QUIETLY_TRUE);
    }

    public static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    public static <S,V> boolean the(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        return the(sampler(subject, query), criteria);
    }

    public static <S> boolean the(S subject, Query<? super S,Boolean> query) {
        return the(subject, query, IS_QUIETLY_TRUE);
    }

    public static <S,V> boolean the(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
        return the(sampler(subject, query), poller, criteria);
    }

    public static <S> boolean the(S subject, Poller poller, Query<? super S,Boolean> query) {
        return the(subject, query, poller, IS_QUIETLY_TRUE);
    }

    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    public static void waitUntil(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    public <V> void waitUntil(Sampler<V> sampler, Matcher<? super V> criteria) {
        waitUntil(sampler, eventually(), criteria);
    }

    public void waitUntil(Sampler<Boolean> sampler) {
        waitUntil(sampler, IS_QUIETLY_TRUE);
    }

    public static <V> void waitUntil(Sampler<V> sampler, Poller poller, Matcher<? super V> criteria) {
        waitUntil(poller, condition(sampler, criteria));
    }

    public static void waitUntil(Sampler<Boolean> sampler, Poller poller) {
        waitUntil(sampler, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> void waitUntil(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        waitUntil(subject, query, eventually(), criteria);
    }

    public <S> void waitUntil(S subject, Query<? super S,Boolean> query) {
        waitUntil(subject, query, IS_QUIETLY_TRUE);
    }

    public static <S,V> void waitUntil(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
        waitUntil(sampler(subject, query), poller, criteria);
    }

    public static <S> void waitUntil(S subject, Poller poller, Query<? super S,Boolean> query) {
        waitUntil(subject, query, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> S when(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        return when(subject, query, eventually(), criteria);
    }

    public <S> S when(S subject, Query<? super S,Boolean> query) {
        return when(subject, query, IS_QUIETLY_TRUE);
    }

    public static <S,V> S when(S subject, Query<? super S, V> query, Poller poller, Matcher<? super V> criteria) {
        waitUntil(subject, query, poller, criteria);
        return subject;
    }

    public static <S> S when(S subject, Poller poller, Query<? super S,Boolean> query) {
        return when(subject, query, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> void when(S subject, Query<? super S,V> query, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, query, eventually(), criteria, action);
    }

    public <S> void when(S subject, Query<? super S,Boolean> query, Action<? super S> action) {
        when(subject, query, IS_QUIETLY_TRUE, action);
    }

    public static <S,V> void when(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, query, poller, criteria);
        action.executeOn(subject);
    }

    public static <S> void when(S subject, Poller poller, Query<? super S,Boolean> query, Action<? super S> action) {
        when(subject, query, poller, IS_QUIETLY_TRUE, action);
    }

    private static <V> Condition condition(Sampler<V> sampler, Matcher<? super V> criteria) {
        return new SamplerMatcherCondition(sampler, criteria);
    }

    private static <S, V> Sampler<V> sampler(S subject, Query<? super S, V> query) {
        return new SubjectQuerySampler<S,V>(subject, query);
    }
}
