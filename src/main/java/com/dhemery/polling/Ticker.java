package com.dhemery.polling;

/**
 * Marks time in fixed-size intervals called "ticks."
 * Tick size is determined by the implementation.
 * Ticks are reckoned from the instant the ticker is started.
 */
public interface Ticker {
    /**
     * Start the ticker.
     */
    void start();

    /**
     * Put this thread to sleep until the next tick.
     */
    void tick();
}
