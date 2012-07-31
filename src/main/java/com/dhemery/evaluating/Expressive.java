package com.dhemery.evaluating;

import com.dhemery.polling.Poll;
import com.dhemery.polling.PollTimeoutException;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

/**
 * Helper methods for making assertions, waiting for conditions,
 * and establishing preconditions before acting on an item.
 *
 */
public abstract class Expressive {
    /**
     * Supplies the default poll used for pollable expressions that do not supply a poll.
	 */
    public abstract Poll eventually();

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
	 * @param poll the poll to evaluate the subject
	 * @param criteria the criteria to satisfy
	 * @throws PollTimeoutException if the poll expires before the subject satisfies the criteria
	 */
    public static <S> void assertThat(S subject, Poll poll, Matcher<? super S> criteria) {
		poll.until(subject, criteria);
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
     * @param poll the poll to evaluate the subject
     * @param criteria the criteria to satisfy
     * @return whether the subject satisfies the criteria before the poll expires
     */
    public static <S> boolean the(S subject, Poll poll, Matcher<? super S> criteria) {
        try {
            poll.until(subject, criteria);
            return true;
        } catch (PollTimeoutException ignored) {
            return false;
        }
    }

    /**
     * Wait until the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the default poll expires before the subject satisfies the criteria
     */
    public <S> void waitUntil(S subject, Matcher<? super S> criteria) {
        waitUntil(subject, eventually(), criteria);
    }

    /**
	 * Wait until the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poll the poll to evaluate the subject
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the poll expires before the subject satisfies the criteria
	 */
    public static <S> void waitUntil(S subject, Poll poll, Matcher<? super S> criteria) {
		poll.until(subject, criteria);
	}

    /**
     * Return the subject when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if the default poll expires before the subject satisfies the criteriacriteria
     */
    public <S> S when(S subject, Matcher<? super S> criteria) {
        return when(subject, eventually(), criteria) ;
    }

    /**
     * Return the subject when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poll the poll to evaluate the subject
     * @param criteria the criteria to satisfy
     * @return the subject
     * @throws PollTimeoutException if the poll expires before the subject satisfies the criteria
     */
    public static <S> S when(S subject, Poll poll, Matcher<? super S> criteria) {
        poll.until(subject, criteria);
        return subject;
    }

    /**
     * Execute an action when the subject satisfies the given criteria.
     * This method uses the default poller supplied by the default poller supplier.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @param action the action to execute when the subject satisfies the criteria
     * @throws PollTimeoutException if the default poll expires before the subject satisfies the criteria
     */
    public <S> void when(S subject, Matcher<? super S> criteria, Action<? super S> action) {
        when(subject, eventually(), criteria, action);
    }

    /**
     * Execute an action when the subject satisfies the given criteria.
     * @param subject the subject to evaluate
     * @param poll the poll to evaluate the subject
     * @param criteria the criteria to satisfy
     * @throws PollTimeoutException if the poll expires before the subject satisfies the criteria.
     */
    public static <S> void when(S subject, Poll poll, Matcher<? super S> criteria, Action<? super S> action) {
        poll.until(subject, criteria);
        action.executeOn(subject);
    }
}
