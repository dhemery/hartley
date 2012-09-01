package com.dhemery.polling;

import com.dhemery.core.Condition;

public class EvaluatingPoller implements Poller {
    private final PollEvaluator evaluator;

    public EvaluatingPoller(PollEvaluator evaluator) {
        this.evaluator = evaluator;
    }
    @Override
    public void poll(Ticker ticker, Condition condition) {
        ticker.start();
        int pollCount = 0;

        do {
            if(evaluator.evaluate(condition, ++pollCount)) return;
            ticker.tick();
        } while(!ticker.isExpired());

        evaluator.fail(condition);
    }
}
