package com.dhemery.evaluating;

import com.dhemery.polling.PollTimeoutException;
import com.dhemery.polling.Poller;
import com.dhemery.polling.PollerSupplier;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

/**
 * Helper methods for making assertions, waiting for conditions,
 * and establishing preconditions before acting on an item.
 *
 */
public abstract class Expressive {
    private final PollerSupplier supplier;

    public Expressive(PollerSupplier supplier){
        this.supplier = supplier;
    }

    /**
     * Return a default poller for expressions to use.
     * Implement this method to supply a default poller for your expressions.
	 * @return the poller to use when an expression does not specify one.
	 */
    private Poller defaultPoller() { return supplier.defaultPoller(); }

    /**
     * Assert that the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     */
    public static <S> void assertThat(S subject, Matcher<? super S> criteria) {
        MatcherAssert.assertThat(subject, criteria);
    }

    /**
	 * Assert that the subject satisfies the given criteria before the poll timer expires.
	 * @param subject the subject to evaluate
	 * @param poller the poller to conduct the poll
	 * @param criteria the criteria to satisfy
	 * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria
	 */
    public static <S> void assertThat(S subject, Poller poller, Matcher<? super S> criteria) {
		poller.poll(subject, criteria);
	}

    /**
     * Determine whether the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @return whether the subject satisfies the criteria
     */
    public static <S> boolean the(S subject, Matcher<? super S> criteria) {
        return criteria.matches(subject);
    }

    /**
     * Determine whether the subject satisfies the given criteria before the poll timer expires.
     * @param subject the subject to evaluate
     * @param poller the timer used to poll
     * @param criteria the criteria to satisfy
     * @return whether the subject satisfies the criteria before the poll timer expires
     */
    public static <S> boolean the(S subject, Poller poller, Matcher<? super S> criteria) {
        try {
            poller.poll(subject, criteria);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Wait until the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria
     */
    public <S> void waitUntil(S subject, Matcher<? super S> criteria) {
        waitUntil(subject, defaultPoller(), criteria);
    }

    /**
	 * Wait until the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poller the timer used to poll
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria
	 */
    public static <S> void waitUntil(S subject, Poller poller, Matcher<? super S> criteria) {
		poller.poll(subject, criteria);
	}

    /**
     * Return the subject when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if the default timer expires before the subject satisfies the criteriacriteria
     */
    public <S> S when(S subject, Matcher<? super S> criteria) {
        return when(subject, defaultPoller(), criteria) ;
    }

    /**
     * Return the subject when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poller the time used to poll
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria
     */
    public static <S> S when(S subject, Poller poller, Matcher<? super S> criteria) {
        poller.poll(subject, criteria);
        return subject;
    }

    /**
     * Execute an action when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @param action the action to execute when the subject satisfies the criteria
     * @throws PollTimeoutException if the default timer expires before the subject satisfies the criteria
     */
    public <S> void when(S subject, Matcher<? super S> criteria, Action<? super S> action) {
        when(subject, defaultPoller(), criteria, action);
    }

    /**
     * Execute an action when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poller the timer used to poll
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria.
     */
    public static <S> void when(S subject, Poller poller, Matcher<? super S> criteria, Action<? super S> action) {
        poller.poll(subject, criteria);
        action.executeOn(subject);
    }
}
