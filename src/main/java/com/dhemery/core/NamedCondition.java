package com.dhemery.core;

/**
 * A condition described by a fixed name.
 */
public abstract class NamedCondition extends Named implements Condition {
    /**
     * Create a named condition.
     */
    public NamedCondition(String name) {
        super(name);
    }
}
