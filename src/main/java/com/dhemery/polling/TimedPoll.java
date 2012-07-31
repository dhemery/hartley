package com.dhemery.polling;

import org.hamcrest.Matcher;

/**
 * A poll that throws an exception if a timer expires before the condition is satisfied.
 */
public class TimedPoll implements Poll {
    private final PollTimer timer;

    /**
     * Create a poll that throws an exception if a timer expires before the condition is satisfied.
     */
    public TimedPoll(PollTimer timer) {
        this.timer = timer;
    }

    @Override
    public void until(Condition condition) {
        timer.start();
        while (!timer.isExpired()) {
            if (condition.isSatisfied()) return;
            timer.tick();
        }
        throw new PollTimeoutException(condition);

    }

    @Override
    public <S> void until(S subject, Matcher<? super S> criteria) {
        until(new MatcherCondition(subject, criteria));
    }
}
