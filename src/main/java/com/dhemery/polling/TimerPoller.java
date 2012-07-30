package com.dhemery.polling;

import org.hamcrest.Matcher;

public class TimerPoller implements Poller {

    private final PollTimer timer;

    public TimerPoller(PollTimer timer) {
        this.timer = timer;
    }

    @Override
    public void poll(Condition condition) {
        timer.start();
        while (!timer.isExpired()) {
            if (condition.isSatisfied()) return;
            timer.tick();
        }
        throw new PollTimeoutException(condition);

    }

    @Override
    public <S> void poll(S subject, Matcher<? super S> criteria) {
        poll(new MatcherCondition(subject, criteria));
    }
}
