package com.dhemery.polling;


import org.hamcrest.Matcher;

/**
 * Poll until a condition is satisfied.
 * 
 * @author Dale Emery
 */
public interface Poll {
	/**
	 * Poll until the condition is satisfied.
	 */
    void until(Condition condition);

    /**
     * Poll until the subject satisfies the criteria.
     */
    <S> void until(S subject, Matcher<? super S> criteria);
}