package com.dhemery.polling;


import org.hamcrest.Matcher;

/**
 * Polls until a condition is satisfied.
 * 
 * @author Dale Emery
 */
public interface Poller {
	/**
	 * Poll until the condition is satisfied.
	 */
    void poll(Condition condition);

    /**
     * Poll until the subject satisfies the criteria.
     */
    <S> void poll(S subject, Matcher<? super S> criteria);
}