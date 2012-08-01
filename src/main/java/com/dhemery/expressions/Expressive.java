package com.dhemery.expressions;

import com.dhemery.core.*;
import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Poller;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public abstract class Expressive {
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

    /**
     * Assert that the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @throws AssertionError if the subject does not satisfy the criteria
     */
    protected static <S> void assertThat(S subject, Matcher<? super S> criteria) {
        MatcherAssert.assertThat(subject, criteria);
    }

    protected static <S,V> void assertThat(S subject, Query<? super S, V> query, Matcher<? super V> criteria) {
        assertThat(query.query(subject), criteria);
    }

    /**
	 * Assert that the subject satisfies the criteria before polling expires.
	 * @param subject the subject to evaluate
	 * @param poller the poller to evaluate the subject
	 * @param criteria the criteria to satisfy
	 * @throws PollTimeoutException if polling expires before the subject satisfies the criteria
	 */
    protected static <S> void assertThat(S subject, Poller poller, Matcher<? super S> criteria) {
		poller.poll(subject, criteria);
	}

    protected static <S,V> void assertThat(S subject, Query<? super S, V> query, Poller poller, Matcher<? super V> criteria) {
        poller.poll(subject, query, criteria);
    }

    /**
     * Assert that the condition is satisfied.
     * @throws AssertionError if the condition is not satisfied
     */
    protected static void assertThat(Condition condition) {
        MatcherAssert.assertThat(condition.toString(), condition.isSatisfied());
    }

    /**
     * Assert that the condition is satisfied before the polling expires.
     * @param poller the poller to evaluate the condition
     * @param condition the condition to evaluate
	 * @throws PollTimeoutException if polling expires before the condition is satisfied
     */
    protected static void assertThat(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    /**
     * Determine whether the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @return whether the subject satisfies the criteria
     */
    protected static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    protected static <S,V> boolean the(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        return the(query.query(subject), criteria);
    }

    /**
     * Determine whether the subject satisfies the criteria before polling expires.
     * @param subject the subject to evaluate
     * @param poller the poller to evaluate the subject
     * @param criteria the criteria to satisfy
     * @return whether the subject satisfies the criteria before polling expires
     */
    protected static <S> boolean the(S subject, Poller poller, Matcher<? super S> criteria) {
        try {
            poller.poll(subject, criteria);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    protected static <S,V> boolean the(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
        try {
            poller.poll(subject, query, criteria);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Wait until the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the default poller expires before the subject satisfies the criteria
     */
    protected <S> void waitUntil(S subject, Matcher<? super S> criteria) {
        waitUntil(subject, eventually(), criteria);
    }

    protected <S,V> void waitUntil(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        waitUntil(subject, query, eventually(), criteria);
    }

    /**
	 * Wait until the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param poller the poller to evaluate the subject
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if polling expires before the subject satisfies the criteria
	 */
    protected static <S> void waitUntil(S subject, Poller poller, Matcher<? super S> criteria) {
		poller.poll(subject, criteria);
	}

    protected static <S,V>  void waitUntil(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
		poller.poll(subject, query, criteria);
	}

    /**
     * Wait until the condition is satisfied.
     * @param condition the condition to evaluate
     * @throws PollTimeoutException if the default poller expires before condition is satisfied
     */
    protected void waitUntil(Condition condition) {
        waitUntil(eventually(), condition);
    }

    /**
     * Wait until the condition is satisfied.
     * @param poller the poller to evaluate the condition
     * @param condition the condition to evaluate
     * @throws PollTimeoutException if polling expires before condition is satisfied
     */
    protected static void waitUntil(Poller poller, Condition condition) {
        poller.poll(condition);
    }

    /**
     * Return the subject when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if the default poller expires before the subject satisfies the criteriacriteria
     */
    protected <S> S when(S subject, Matcher<? super S> criteria) {
        return when(subject, eventually(), criteria) ;
    }

    protected <S,V>  S when(S subject, Query<? super S,V> query, Matcher<? super V> criteria) {
        return when(subject, query, eventually(), criteria);
    }

    /**
     * Return the subject when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param poller the poller to evaluate the subject
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if polling expires before the subject satisfies the criteria
     */
    protected static <S> S when(S subject, Poller poller, Matcher<? super S> criteria) {
        poller.poll(subject, criteria);
        return subject;
    }

    protected <S,V>  S when(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria) {
        poller.poll(subject, query, criteria);
        return subject;
    }

    /**
     * Execute the action on the subject when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @param action the action to execute on the subject
     * @throws PollTimeoutException if the default poller expires before the subject satisfies the criteria
     */
    protected <S> void when(S subject, Matcher<? super S> criteria, Action<? super S> action) {
        when(subject, eventually(), criteria, action);
    }

    /**
     * Execute the action on the subject when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param poll the poll to evaluate the subject
     * @param criteria the criteria to satisfy
     * @param action the action to execute on the subject
     * @throws PollTimeoutException if polling expires before the subject satisfies the criteria.
     */
    protected static <S> void when(S subject, Poller poll, Matcher<? super S> criteria, Action<? super S> action) {
        poll.poll(subject, criteria);
        action.executeOn(subject);
    }

    protected <S,V>  void when(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria, Action<? super S> action) {
        poller.poll(subject, query, criteria);
        action.executeOn(subject);
    }

    /**
     * Run the runnable when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @param runnable the runnable to run
     * @throws PollTimeoutException if the default poller expires before the subject satisfies the criteria
     */
    protected <S> void when(S subject, Matcher<? super S> criteria, Runnable runnable) {
        when(subject, eventually(), criteria, runnable);
    }

    protected <S,V>  void when(S subject, Query<? super S,V> query, Matcher<? super V> criteria, Runnable runnable) {
        when(subject, query, eventually(), criteria, runnable);
    }

    /**
     * Run the runnable when the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param poller the poller to eveluate the subject
     * @param criteria the criteria to satisfy
     * @param runnable the runnable to run
     * @throws PollTimeoutException if polling expires before the subject satisfies the criteria
     */
    protected static <S> void when(S subject, Poller poller, Matcher<? super S> criteria, Runnable runnable) {
        poller.poll(subject, criteria);
        runnable.run();
    }

    protected <S,V>  void when(S subject, Query<? super S,V> query, Poller poller, Matcher<? super V> criteria, Runnable runnable) {
        poller.poll(subject, query, criteria);
        runnable.run();
    }

    /**
     * Run the runnable when the condition is satisfied.
     * @param condition the condition to evaluate
     * @param runnable the runnable to run
     * @throws PollTimeoutException if the default poller expires before the condition is satisfied
     */
    protected void when(Condition condition, Runnable runnable) {
        when(eventually(), condition, runnable);
    }

    /**
     * Run the runnable when the condition is satisfied.
     * @param poller the poller to evaluate the condition
     * @param condition the condition to evaluate
     * @param runnable the runnable to run
     * @throws PollTimeoutException if polling expires before the condition is satisfied
     */
    protected static void when(Poller poller, Condition condition, Runnable runnable) {
        poller.poll(condition);
        runnable.run();
    }

}
