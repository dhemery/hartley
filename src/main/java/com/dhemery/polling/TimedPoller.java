package com.dhemery.polling;

/**
 * A poller that throws an exception if a timer expires before the condition is satisfied.
 */
public class TimedPoller implements Poller {
    private final PollTimer timer;

    /**
     * Create a poller that throws an exception if the timer expires before the condition is satisfied.
     */
    public TimedPoller(PollTimer timer) {
        this.timer = timer;
    }

    @Override
    public void poll(Condition condition) {
        timer.start();
        while (!timer.isExpired()) {
            if(condition.isSatisfied()) return;
            timer.tick();
        }
        throw new PollTimeoutException(condition);
    }
}
