package com.dhemery.polling;

public interface PollEvaluator {
    boolean evaluate(Condition condition, long failureCount);
}
