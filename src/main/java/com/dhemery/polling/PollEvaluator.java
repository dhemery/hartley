package com.dhemery.polling;

import com.dhemery.core.Condition;

public interface PollEvaluator {
    boolean evaluate(Condition condition, int pollCount);
    void fail(Condition condition);
}
