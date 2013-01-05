package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

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
        super("defaulting to " + descriptionOf(defaultValue));
        this.defaultValue = defaultValue;
    }

    @Override
    protected T operateOnNull() {
        return defaultValue;
    }

    private static <T> String descriptionOf(T defaultValue) {
        Description description = new StringDescription();
        description.appendValue(defaultValue);
        return description.toString();
    }
}
