package com.dhemery.polling;

public class ASimplePollEvaluator extends APollEvaluator {
    @Override
    protected PollEvaluator evaluator() {
        return new SimplePollEvaluator();
    }
}
