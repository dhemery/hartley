package com.dhemery.core;

/**
 * Repeats an action until a condition is satisfied.
 * Note that {@code Until} checks the condition before performing the action.
 * @see Repeat
 */
public class Until {
    private final Condition condition;

    /**
     * Create an until that repeats until the given condition is satisfied.
     */
    public Until(Condition condition) {
        this.condition = condition;
    }

    /**
     * Create an until that repeats until the given condition is satisfied.
     */
    public static Until until(Condition condition) {
        return new Until(condition);
    }

    /**
     * Repeat the given action until the until's condition is satisfied.
     */
    public void repeat(Runnable action) {
        while(!condition.isSatisfied()) action.run();
    }
}
