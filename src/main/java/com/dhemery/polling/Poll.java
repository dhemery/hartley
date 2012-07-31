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
     * Poll until either the subject satisfies the criteria.
     * @param subject the subject to evaluate
     * @param criteria the criteria to satisfy
     * @param <S> the type of subject
     */
    <S> void until(S subject, Matcher<? super S> criteria);
}