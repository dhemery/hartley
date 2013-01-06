package com.dhemery.core;

/**
 * An object that describes itself by a fixed name.
 */
public class Named {
    private final String name;

    /**
     * Create an object with a fixed name.
     */
    public Named(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
