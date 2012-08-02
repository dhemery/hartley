package com.dhemery.polling;

import com.dhemery.core.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import static com.dhemery.polling.BooleanSugar.*;

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
     * assertThat(theCheshireCat, eventually(), is(grinning()));
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

    public static <V> void assertThat(Sampled<V> sampled, Matcher<? super V> criteria) {
        assertThat(sampleOf(sampled, criteria));
    }

    public static void assertThat(Sampled<Boolean> sampled) {
        assertThat(sampled, IS_QUIETLY_TRUE);
    }

    public static <V> void assertThat(Sampled<V> sampled, Poller poller, Matcher<? super V> criteria) {
        assertThat(poller, sampleOf(sampled, criteria));
    }

    public static void assertThat(Poller poller, Sampled<Boolean> sampled) {
        assertThat(sampled, poller, IS_QUIETLY_TRUE);
    }

    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), criteria);
    }

    public static <S> void assertThat(S subject, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, IS_QUIETLY_TRUE);
    }

    public static <S,V> void assertThat(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        assertThat(sampled(subject, feature), poller, criteria);
    }

    public static <S> void assertThat(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        assertThat(subject, feature, poller, IS_QUIETLY_TRUE);
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

    public static <V> boolean the(Sampled<V> sampled, Matcher<? super V> criteria) {
        return the(sampleOf(sampled, criteria));
    }

    public static boolean the(Sampled<Boolean> sampled) {
        return the(sampled, IS_QUIETLY_TRUE);
    }

    public static <V> boolean the(Sampled<V> sampled, Poller poller, Matcher<? super V> criteria) {
        return the(sampleOf(sampled, criteria), poller);
    }

    public static boolean the(Sampled<Boolean> sampled, Poller poller) {
        return the(sampled, poller, IS_QUIETLY_TRUE);
    }

    public static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    public static <S,V> boolean the(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), criteria);
    }

    public static <S> boolean the(S subject, Feature<? super S,Boolean> feature) {
        return the(subject, feature, IS_QUIETLY_TRUE);
    }

    public static <S,V> boolean the(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        return the(sampled(subject, feature), poller, criteria);
    }

    public static <S> boolean the(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        return the(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    public void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    public static void waitUntil(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    public <V> void waitUntil(Sampled<V> sampled, Matcher<? super V> criteria) {
        waitUntil(sampled, eventually(), criteria);
    }

    public void waitUntil(Sampled<Boolean> sampled) {
        waitUntil(sampled, IS_QUIETLY_TRUE);
    }

    public static <V> void waitUntil(Sampled<V> sampled, Poller poller, Matcher<? super V> criteria) {
        waitUntil(poller, sampleOf(sampled, criteria));
    }

    public static void waitUntil(Sampled<Boolean> sampled, Poller poller) {
        waitUntil(sampled, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        waitUntil(subject, feature, eventually(), criteria);
    }

    public <S> void waitUntil(S subject, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, IS_QUIETLY_TRUE);
    }

    public static <S,V> void waitUntil(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria) {
        waitUntil(sampled(subject, feature), poller, criteria);
    }

    public static <S> void waitUntil(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        waitUntil(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> S when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria) {
        return when(subject, feature, eventually(), criteria);
    }

    public <S> S when(S subject, Feature<? super S,Boolean> feature) {
        return when(subject, feature, IS_QUIETLY_TRUE);
    }

    public static <S,V> S when(S subject, Feature<? super S, V> feature, Poller poller, Matcher<? super V> criteria) {
        waitUntil(subject, feature, poller, criteria);
        return subject;
    }

    public static <S> S when(S subject, Poller poller, Feature<? super S,Boolean> feature) {
        return when(subject, feature, poller, IS_QUIETLY_TRUE);
    }

    public <S,V> void when(S subject, Feature<? super S,V> feature, Matcher<? super V> criteria, Action<? super S> action) {
        when(subject, feature, eventually(), criteria, action);
    }

    public <S> void when(S subject, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, IS_QUIETLY_TRUE, action);
    }

    public static <S,V> void when(S subject, Feature<? super S,V> feature, Poller poller, Matcher<? super V> criteria, Action<? super S> action) {
        waitUntil(subject, feature, poller, criteria);
        action.executeOn(subject);
    }

    public static <S> void when(S subject, Poller poller, Feature<? super S,Boolean> feature, Action<? super S> action) {
        when(subject, feature, poller, IS_QUIETLY_TRUE, action);
    }

    public static <S> Matcher<S> is(S value) {
        return Matchers.is(value);
    }

    public static <S> Matcher<S> is(Matcher<S> matcher) {
        return Matchers.is(matcher);
    }

    public static <S> Feature<S, Boolean> is(Feature<? super S, Boolean> feature) {
        return featureIs(feature);
    }

    public static Sampled<Boolean> is(Sampled<Boolean> sampled) {
        return sampledIs(sampled);
    }

    public static Condition is(Condition condition) {
        return conditionIs(condition);
    }

    public static <S> Matcher<S> not(S value) {
        return Matchers.not(value);
    }

    public static <S> Matcher<S> not(Matcher<S> matcher) {
        return Matchers.not(matcher);
    }
    
    public static <S> Feature<S,Boolean> not(Feature<? super S, Boolean> feature) {
        return featureNot(feature);
    }

    public static Sampled<Boolean> not(Sampled<Boolean> sampled) {
        return sampledNot(sampled);
    }

    public static Condition not(Condition condition) {
        return conditionNot(condition);
    }

    private static <S, V> Sampled<V> sampled(S subject, Feature<? super S, V> feature) {
        return new FeatureSampler<S,V>(subject, feature);
    }

    private static <V> Condition sampleOf(Sampled<V> sampled, Matcher<? super V> criteria) {
        return new SamplingCondition<V>(sampled, criteria);
    }
}
