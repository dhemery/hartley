package com.dhemery.core;

import org.hamcrest.Description;

/**
 * An operator that supplies a default value if its operand is {@code null}.
 * If the operand is non-null, the operator returns the operand.
 * @param <T> the type of operand
 */
public class DefaultingTo<T> extends NullSafeUnaryOperator<T> {
    private final T defaultValue;

    /**
     * Create an operator that supplies the given default value if its operand is null.
     */
    public DefaultingTo(T defaultValue) {
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
