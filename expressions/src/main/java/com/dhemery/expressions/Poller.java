package com.dhemery.expressions;

/**
 * Poll a condition until it is satisfied.
 */
public interface Poller {
    /**
     * Poll the condition on the given polling schedule.
     * @return {@code true} if the condition is satisfied before the polling schedule expires,
     * otherwise {@code false}.
     */
    boolean poll(PollingSchedule schedule, Condition condition);

    /**
     * Poll the condition on the default polling schedule.
     * @return {@code true} if the condition is satisfied before the default polling schedule expires,
     * otherwise {@code false}.
     * @see #eventually()
     */
    default boolean poll(Condition condition) {
        return poll(eventually(), condition);
    }

    /**
     * Return the default polling schedule.
     * @return the default polling schedule
     */
    PollingSchedule eventually();
}
