package com.dhemery.core;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create operators.
 */
public class OperatorExpressions {
    public static <T> UnaryOperator<T> defaultingTo(T defaultValue) {
        return new DefaultValue<T>(defaultValue);
    }

    public static <T> UnaryOperator<T> requiring(Matcher<? super T>... criteria) {
        return new Requiring<T>(allOf(criteria));
    }

    public static UnaryOperator<String> trimmed() {
        return new TrimString();
    }
}
