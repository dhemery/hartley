package com.dhemery.core;

public class ActionRunner<S> implements Runnable {
    private final S subject;
    private final Action<? super S> action;

    public ActionRunner(S subject, Action<? super S> action) {
        this.subject = subject;
        this.action = action;
    }

    @Override
    public void run() {
        action.actOn(subject);
    }

    public static <S> Runnable run(S subject, Action<? super S> action) {
        return new ActionRunner(subject, action);
    }
}
