package com.dhemery.polling;

/**
 * Marks time in fixed-size intervals called "ticks"
 * and expires at some instant after starting.
 * Ticks are reckoned from the instant the ticker is started.
 * Tick size is determined by the implementation.
 * The expiration time is determined by the implementation.
 */
public interface Ticker {
    /**
     * Report whether the ticker is expired.
     */
    boolean isExpired();

    /**
     * Start the ticker.
     */
    void start();

    /**
     * Put this thread to sleep until the next tick.
     */
    void tick();
}
