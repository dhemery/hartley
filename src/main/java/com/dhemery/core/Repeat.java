package com.dhemery.core;

/**
 * Repeats an action until a condition is satisfied.
 * Note that {@code Repeat} performs the action before checking the condition.
 * @see Until
 */
public class Repeat {
    private final Runnable action;

    /**
     * Create a repeater that repeats the given action.
     */
    public Repeat(Runnable action) {
        this.action = action;
    }

    /**
     * Create a repeater that repeats the given action.
     */
    public static Repeat repeat(Runnable action) {
        return new Repeat(action);
    }

    /**
     * Repeat the repeater's action until the given condition is satisfied.
     */
    public void until(Condition condition){
        do { action.run(); } while(!condition.isSatisfied());
    }
}
