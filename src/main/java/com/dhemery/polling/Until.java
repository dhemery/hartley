package com.dhemery.polling;

import com.dhemery.core.Condition;

public class Until {
    private final Condition condition;

    public Until(Condition condition) {
        this.condition = condition;
    }

    public static Until until(Condition condition) {
        return new Until(condition);
    }

    public void repeat(Runnable action) {
        while(!condition.isSatisfied()) action.run();
    }
}
