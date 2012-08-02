package com.dhemery.polling;

/**
 * A timed poller that delegates evaluation to an evaluator.
 */
public class DelegatingPoller implements Poller {
    private final PollTimer timer;
    private final PollEvaluator evaluator;

    /**
     * Create a timed poller that delegates evaluation to an evaluator.
     */
    public DelegatingPoller(PollTimer timer, PollEvaluator evaluator) {
        this.timer = timer;
        this.evaluator = evaluator;
    }

    @Override
    public void poll(Condition condition) {
        timer.start();
        int failureCount = 0;
        while (!timer.isExpired()) {
            if(evaluator.evaluate(condition, failureCount)) return;
            failureCount++;
            timer.tick();
        }
        throw new PollTimeoutException(condition);
    }
}
