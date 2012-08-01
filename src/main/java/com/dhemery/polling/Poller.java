package com.dhemery.polling;


import com.dhemery.core.Condition;
import com.dhemery.core.Query;
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

    <S,V> void poll(S subject, Query<? super S,V> query, Matcher<? super V> criteria);

    <V> void poll(Probe<? extends V> probe, Matcher<? super V> criteria);
}