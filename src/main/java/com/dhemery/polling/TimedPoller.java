package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.core.Query;
import org.hamcrest.Matcher;

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

    /**
     * Poll until the subject satisfies the criteria or the poll timer expires.
     * @throws PollTimeoutException if the timer expires before the subject satisfies the criteria
     */
    @Override
    public <S> void poll(S subject, Matcher<? super S> criteria) {
        poll(new MatcherCondition<S>(subject, criteria));
    }

    @Override
    public <S, V> void poll(S subject, Query<? super S, V> query, Matcher<? super V> criteria) {
        poll(new SubjectQueryProbe<S,V>(subject, query), criteria);
    }

    @Override
    public <V> void poll(Probe<? extends V> probe, Matcher<? super V> criteria) {
        poll(new ProbingCondition<V>(probe, criteria));
    }

    /**
     * Poll until the condition is satisfied or the poll timer expires.
     * @throws PollTimeoutException if the timer expires before the condition is satisfied
     */
    @Override
    public void poll(Condition condition) {
        timer.start();
        while (!timer.isExpired()) {
            if (condition.isSatisfied()) return;
            timer.tick();
        }
        throw new PollTimeoutException(condition);

    }
}
