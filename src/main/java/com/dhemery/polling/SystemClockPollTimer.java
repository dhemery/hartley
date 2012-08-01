package com.dhemery.polling;

import java.util.concurrent.TimeUnit;

/**
 * A PollTimer that uses the system clock to reckon time.
 * 
 * @author Dale Emery
 */
public class SystemClockPollTimer implements PollTimer {
	private static final long ONE_SECOND = 1000;
	private final long durationInMilliseconds;
	private final long tickSizeInMilliseconds;
    private long startTime;

	/**
	 * Create a timer with a tick size of one second.
	 * 
	 * @param durationInMilliseconds the poll duration.
	 */
	public SystemClockPollTimer(long durationInMilliseconds) {
		this(durationInMilliseconds, ONE_SECOND);
	}

	/**
	 * @param durationInMilliseconds the poll duration.
	 * @param tickSizeInMilliseconds the tick size.
	 */
	public SystemClockPollTimer(long durationInMilliseconds, long tickSizeInMilliseconds) {
		this.durationInMilliseconds = durationInMilliseconds;
		this.tickSizeInMilliseconds = tickSizeInMilliseconds;
	}

	@Override
	public long elapsedTicks() {
		return elapsedTime() / tickSizeInMilliseconds;
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
