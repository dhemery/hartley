package com.dhemery.polling;

/**
 * A timer used for polling.
 * 
 * A timer has a poll duration.
 * The timer expires when
 * the time that has passed since the timer was started
 * exceeds the duration.
 * 
 * A timer divides time into fixed-size polling intervals called ticks. Ticks
 * are reckoned from the instant the timer is started.
 * 
 * @author Dale Emery
 */
public interface PollTimer {
	/**
	 * @return the number of ticks that have elapsed since the timer was started.
	 */
	long elapsedTicks();

	/**
	 * @return whether the poll duration has passed since the timer was started.
	 */
    boolean isExpired();

	/**
	 * Start the timer ticking. 
	 */
	void start();

	/**
	 * Put this thread to sleep until the next tick.
	 */
	void tick();
}
