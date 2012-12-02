package com.dhemery.polling;

import com.dhemery.core.Condition;

public class Repeat {
    private final Runnable action;

    public Repeat(Runnable action) {
        this.action = action;
    }

    public static Repeat repeat(Runnable action) {
        return new Repeat(action);
    }

    public void until(Condition condition){
        do { action.run(); } while(!condition.isSatisfied());
    }
}
