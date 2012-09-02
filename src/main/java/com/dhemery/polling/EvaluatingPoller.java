package com.dhemery.polling;

import com.dhemery.core.Condition;

/**
 * A poller that delegates evaluation to a {@link PollEvaluator}.
 */
public class EvaluatingPoller implements Poller {
    private final PollEvaluator evaluator;

    /**
     * Create a poller that uses the evaluator to evaluate the condition.
     */
    public EvaluatingPoller(PollEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Poll until either the condition is satisfied or the ticker expires.
     * @param ticker a ticker to control the polling
     * @param condition the condition to poll
     * @throws PollTimeoutException if the ticker expires before the condition is satisfied
     */
    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();

        do {
            if(evaluator.evaluate(condition)) return;
            ticker.tick();
        } while(!ticker.isExpired());

        evaluator.fail(condition);
    }
}
