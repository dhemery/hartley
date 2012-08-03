package com.dhemery.polling;

/**
 * An expiring poller that delegates evaluation to an evaluator.
 */
public class DelegatingExpiringPoller implements Poller {
    private final ExpiringTicker ticker;
    private final PollEvaluator evaluator;

    /**
     * Create an expiring poller that delegates evaluation to the evaluator.
     */
    public DelegatingExpiringPoller(ExpiringTicker ticker, PollEvaluator evaluator) {
        this.ticker = ticker;
        this.evaluator = evaluator;
    }

    /**
     * Poll until the condition is satisfied or the ticker expires.
     */
    @Override
    public void poll(Condition condition) {
        ticker.start();
        int failureCount = 0;
        while (!ticker.isExpired()) {
            if(evaluator.evaluate(condition, failureCount)) return;
            failureCount++;
            ticker.tick();
        }
        throw new PollTimeoutException(condition);
    }
}
