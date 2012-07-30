package com.dhemery.polling;


import org.hamcrest.Matcher;

/**
 * Poll until either a condition is satisfied or a timer expires.
 * 
 * @author Dale Emery
 */
public interface Poller {
	/**
	 * Poll until either a condition is satisfied or a timer expires.
	 */
    void poll(Condition condition);
    <S> void poll(S subject, Matcher<? super S> criteria);
}