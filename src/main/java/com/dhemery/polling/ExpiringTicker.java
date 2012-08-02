package com.dhemery.polling;

/**
 * A ticker that expires.
 */
public interface ExpiringTicker extends Ticker {
	/**
     * Report whether the ticker is expired.
	 */
    boolean isExpired();
}
