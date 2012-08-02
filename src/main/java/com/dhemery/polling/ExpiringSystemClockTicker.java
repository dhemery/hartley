package com.dhemery.polling;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * An expiring ticker that uses the system clock to reckon time.
 * The ticker has a duration and a tick size, both in milliseconds.
 * </p>
 * <p>
 * The ticker expires at its start time plus its duration.
 * </p>
 * <p>
 * Note that even after the ticker expires,
 * it will continue to tick when requested.
 * </p>
 */
public class ExpiringSystemClockTicker implements ExpiringTicker {
	private static final long ONE_SECOND = 1000;
	private final long durationInMilliseconds;
	private final long tickSizeInMilliseconds;
    private long startTime;

	/**
	 * Create an expiring ticker with the given duration and a tick size of one second.
	 */
	public ExpiringSystemClockTicker(long durationInMilliseconds) {
		this(durationInMilliseconds, ONE_SECOND);
	}

	/**
     * Create an expiring ticker with the given duration and tick size.
	 */
	public ExpiringSystemClockTicker(long durationInMilliseconds, long tickSizeInMilliseconds) {
		this.durationInMilliseconds = durationInMilliseconds;
		this.tickSizeInMilliseconds = tickSizeInMilliseconds;
	}

    @Override
    public boolean isExpired() {
        return elapsedTime() >= durationInMilliseconds;
    }

    @Override
    public void start() {
        startTime = now();
    }

    @Override
    public void tick() {
        sleep(intervalUntilNextTick());
    }

    @Override
    public String toString() {
        return String.format("duration %s tick size %s", durationInMilliseconds, tickSizeInMilliseconds);
    }

    private long elapsedTime() {
		return now() - startTime;
	}

	private long intervalUntilNextTick() {
		long timeElapsedSincePreviousTick = elapsedTime() % tickSizeInMilliseconds;
		return tickSizeInMilliseconds - timeElapsedSincePreviousTick;
	}

	private long now() {
		return System.currentTimeMillis();
	}

	private void sleep(long durationInMilliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(durationInMilliseconds);
		} catch (InterruptedException ignored) {
		}
	}
}
