package com.dhemery.core;

/**
 * A condition that describes itself with a fixed name.
 */
public abstract class NamedCondition extends Named implements Condition {
    /**
     * Create a named condition.
     */
    public NamedCondition(String name) {
        super(name);
    }
}
