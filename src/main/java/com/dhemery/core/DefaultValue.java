package com.dhemery.core;

import org.hamcrest.Description;

/**
 * An operator that supplies a default value if its operand is null.
 * @param <T> the type of operand
 */
public class DefaultValue<T> extends NullSafeUnaryOperator<T> {
    private final T defaultValue;

    /**
     * Create an operator that supplies the given default value if its operand is null.
     */
    public DefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    protected T defaultValue() {
        return defaultValue;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("defaulting to ")
                .appendValue(defaultValue);
    }
}
