package com.dhemery.configuring;

/**
 * A modifiable set of named options.
 */
public interface ModifiableOptions extends Options {
    /**
     * Define an option by supplying a value.
     * If the option is already defined,
     * the given value replaces the old one.
     */
    void define(String name, String value);
}
